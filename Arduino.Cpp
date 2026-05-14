#include <Servo.h>

// Definición de pines para el sensor ultrasónico HC-SR04
const int pinTrigger = 5;
const int pinEcho = 6;

// Definición de pin para el Servomotor
const int pinServo = 9;

// Instancia del servomotor
Servo miServo;

// Configuración de ángulos fijada a 90 grados para apertura
const int ANGULO_CERRADO = 0;
const int ANGULO_ABIERTO = 90;

// Umbral de detección en centímetros
const int DISTANCIA_UMBRAL = 6;

void setup()
{
    Serial.begin(9600);

    // Configuración de pines del ultrasónico
    pinMode(pinTrigger, OUTPUT);
    pinMode(pinEcho, INPUT);

    // Aseguramos que el Trigger inicie apagado
    digitalWrite(pinTrigger, LOW);

    // Vinculamos el servo e inicia cerrado
    miServo.attach(pinServo);
    miServo.write(ANGULO_CERRADO);
}

void loop()
{
    // Verificamos si Java envió algún dato por Bluetooth
    if (Serial.available() > 0)
    {
        char seleccion = Serial.read();

        // Validamos que sea una letra de residuo válida antes de abrir
        if (seleccion == 'P' || seleccion == 'C' || seleccion == 'M' || seleccion == 'O' || seleccion == 'V' || seleccion == 'B' || seleccion == 'R')
        {

            // 1. Abrir el bote a 90 grados sin importar el tipo de residuo
            miServo.write(ANGULO_ABIERTO);
            delay(600); // Pausa para que el servo llegue a la posición

            // 2. Bloqueo de espera: Quedarse aquí mientras la distancia sea MAYOR a 6 cm
            // En cuanto detecte algo a 6 cm o menos, el bucle rompe.
            long distancia = calcularDistancia();
            while (distancia > DISTANCIA_UMBRAL || distancia == 0)
            {
                delay(60); // Muestreo constante cada 60ms
                distancia = calcularDistancia();
            }

            System.out.println("Objeto detectado por el ultrasónico a: " + distancia + " cm");

            // 3. Notificar el éxito específico de vuelta a Java según la letra que entró
            if (seleccion == 'P')
                Serial.println("PLASTICO");
            else if (seleccion == 'C')
                Serial.println("CARTON");
            else if (seleccion == 'M')
                Serial.println("METAL");
            else if (seleccion == 'O')
                Serial.println("ORGANICO");
            else if (seleccion == 'V')
                Serial.println("VIDRIO");
            else if (seleccion == 'B')
                Serial.println("PILAS");
            else if (seleccion == 'R')
                Serial.println("DIFICIL_RECICLAJE");

            // 4. Esperar a que el desecho termine de caer por completo y cerrar la compuerta
            delay(1500);
            miServo.write(ANGULO_CERRADO);
        }
    }
}

// Función matemática para activar el ultrasónico y calcular la distancia en cm
long calcularDistancia()
{
    // Limpiamos el pin emitiendo un pulso bajo
    digitalWrite(pinTrigger, LOW);
    delayMicroseconds(2);

    // Emitimos el pulso ultrasónico de 10 microsegundos
    digitalWrite(pinTrigger, HIGH);
    delayMicroseconds(10);
    digitalWrite(pinTrigger, LOW);

    // Medimos el tiempo que tarda en regresar el eco (en microsegundos)
    long tiempo = pulseIn(pinEcho, HIGH, 30000); // Timeout de 30ms por seguridad

    // Calculamos la distancia basándonos en la velocidad del sonido (343 m/s)
    // Fórmula: Distancia = (Tiempo * 0.0343) / 2
    long distanciaCm = tiempo * 0.0343 / 2;

    return distanciaCm;
}