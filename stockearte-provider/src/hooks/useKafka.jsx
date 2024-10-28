import { useState, useEffect } from 'react'

export const useKafka = (topic) => {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
      // Simula la conexión a Kafka y la recepción de mensajes
      const interval = setInterval(() => {
        // En una implementación real, aquí conectarías con tu backend que maneja Kafka
        fetch(`/api/kafka/${topic}`)
          .then(response => response.json())
          .then(data => setMessages(prevMessages => [...prevMessages, ...data]));
      }, 5000);
  
      return () => clearInterval(interval);
    }, [topic]);
  
    const sendMessage = async (message) => {
      // En una implementación real, aquí enviarías el mensaje a tu backend para publicarlo en Kafka
      await fetch(`/api/kafka/${topic}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(message),
      });
    };
  
    return { messages, sendMessage };
}
