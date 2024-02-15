# AREP TALLER 3

Juan Pablo Poveda Cañón

Desarrollar un servidor web que respalde características análogas a las de Spark, permitiendo la registración de servicios GET y POST mediante el uso de funciones lambda.

## Arquitectura

En esta instancia, se emplea una arquitectura de servidor web del tipo "microframework", optando por un marco más pequeño y liviano que suministra únicamente las funciones esenciales necesarias para construir aplicaciones web de manera ágil y específica.

* Minimalismo: El código del proyecto utiliza Spark, un microframework Java que se destaca por su minimalismo y simplicidad. Se aprecia cómo se define el enrutamiento de solicitudes y se gestionan las respuestas HTTP de forma directa, evitando abstracciones innecesarias.

* Enrutamiento Simple: En el código, se evidencia la definición del enrutamiento de solicitudes mediante el uso del método get() de Spark. Este método vincula una ruta de URL con un controlador de ruta específico, simplificando así el manejo de las solicitudes entrantes.

* Flexibilidad y Elección: La integración de diversos tipos de respuestas, como JSON, HTML y archivos, según las necesidades de rutas específicas, demuestra la flexibilidad de Spark para adaptarse a diferentes tipos de respuestas.
