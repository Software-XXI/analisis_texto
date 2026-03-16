import pika
import json
import time
from transformers import pipeline

# --- CONFIGURACIÓN DE IA ---
print("🤖 Cargando modelo de IA (BERT Multilingual)...")
# Usamos un pipeline de análisis de sentimiento
# La primera vez descargará aprox 500MB
analyzer = pipeline(
    "sentiment-analysis", 
    model="nlptown/bert-base-multilingual-uncased-sentiment"
)

def process_text(text):
    """Lógica de IA para procesar el texto"""
    result = analyzer(text)[0]
    # El modelo devuelve etiquetas como '1 star', '5 stars', etc.
    stars = int(result['label'].split()[0])
    
    if stars >= 4:
        sentiment = "POSITIVE"
    elif stars <= 2:
        sentiment = "NEGATIVE"
    else:
        sentiment = "NEUTRAL"
    
    # Keyword extraction simple (mejorable después del lunes)
    keywords = [w.strip(",.!?") for w in text.split() if len(w) > 4]
    
    return {
        "sentiment": sentiment,
        "score": round(result['score'], 4),
        "stars": stars,
        "keywords": list(set(keywords))[:5]
    }

# --- CONFIGURACIÓN DE RABBITMQ ---
def main():
    # En local usa 'localhost', en Docker usaremos el nombre del servicio 'rabbitmq'
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    # Declaramos las colas (por si no existen)
    channel.queue_declare(queue='analysis_request', durable=True)
    channel.queue_declare(queue='analysis_response', durable=True)

    def callback(ch, method, properties, body):
        print(f"📩 Recibido: {body.decode()}")
        data = json.loads(body)
        
        # 1. Procesar con IA
        text_to_analyze = data.get("text", "")
        analysis_result = process_text(text_to_analyze)
        
        # 2. Preparar respuesta según el contrato
        response = {
            "id": data.get("id"),
            "sentiment": analysis_result["sentiment"],
            "score": analysis_result["score"],
            "analysis": {
                "keywords": analysis_result["keywords"],
                "stars": analysis_result["stars"]
            },
            "status": "success"
        }

        # 3. Enviar de vuelta a Java
        channel.basic_publish(
            exchange='',
            routing_key='analysis_response',
            body=json.dumps(response),
            properties=pika.BasicProperties(delivery_mode=2) # Mensaje persistente
        )
        
        print(f"✅ Procesado y enviado: {response['id']}")
        # Confirmar a RabbitMQ que el mensaje fue procesado
        ch.basic_ack(delivery_tag=method.delivery_tag)

    # Configurar para que solo reciba 1 mensaje a la vez (no saturar la RAM)
    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(queue='analysis_request', on_message_callback=callback)

    print('🚀 Worker esperando mensajes en [analysis_request]. Presiona CTRL+C para salir.')
    channel.start_consuming()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrumpido por el usuario')