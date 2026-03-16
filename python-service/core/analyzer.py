from transformers import pipeline

class SentimentAnalyzer:
    def __init__(self):
        # Usamos un modelo multi-idioma (Spanish/English) 
        # 'pysentimiento' es excelente para español, o 'nlptown/bert-base-multilingual-uncased-sentiment'
        self.classifier = pipeline("sentiment-analysis", model="nlptown/bert-base-multilingual-uncased-sentiment")

    def analyze(self, text: str):
        # 1. Analizar sentimiento
        result = self.classifier(text)[0] # Devuelve label (1-5 stars) y score
        
        # Mapeo de estrellas a Sentimiento Humano
        stars = int(result['label'].split()[0])
        sentiment = "POSITIVE" if stars > 3 else "NEGATIVE" if stars < 3 else "NEUTRAL"
        
        # 2. Extraer Keywords (Lógica simple para el lunes: sustantivos más frecuentes)
        # Nota: Para el lunes, puedes usar un set de palabras clave, 
        # luego lo escalamos a algo más pesado.
        keywords = [word for word in text.split() if len(word) > 5][:5] 
        
        return {
            "sentiment": sentiment,
            "score": round(result['score'], 4),
            "keywords": keywords
        }