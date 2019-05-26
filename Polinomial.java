package Clases;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Polinomial {
    private float[] array;
    private String polinomioDesordenado;

    // Constructor per defecte. Genera un polinomi zero
    public Polinomial() {
        this.array = new float[]{0};
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array// este recibe el primer test de todos
    public Polinomial(float[] cfs) {
        float[] resultado = new float[cfs.length];
        for (int i = 0; i < cfs.length; i++) {
            resultado[i] = cfs[i];
        }
        //en si solo hay ceros en el array, se devuelve un array de una posicion que sea 0
        int temp = 0;
        if (resultado[0] == 0 && resultado.length > 1){
            for (int i = 0; i < resultado.length; i++) {
                if (resultado[i] == 0){
                    temp++;
                }
            }
        }
        if (temp == resultado.length){
            float[] cero = new float[1];
            cero[0] = 0;
            this.array = cero;
        } else {
            this.array = resultado;
        }
    }

    public float[] getArray() {
        return array;
    }

    public void setArray(float[] array) {
        this.array = array;
    }

    // Constructor a partir d'un string
    public Polinomial(String s) {
        //quita los espacios
        this.polinomioDesordenado = s.replaceAll("\\s+", "");
        if (s == "0" && this.polinomioDesordenado == "0"){
        }
        //esas P son para evitar un index out of bounds por ser -1 o pasarse
        polinomioDesordenado = "P" + polinomioDesordenado + "P";
        HashMap<Integer, Integer> unNomio = new HashMap<>();
        for (int i = 0; i < polinomioDesordenado.length(); i++) { //
            //si es x
            if (polinomioDesordenado.charAt(i) == 'x') {

                //el else de este da igual
                //si la posicion anterior a i no es 0
                if (polinomioDesordenado.charAt(i - 1) != '0') {
                    StringBuilder tempNumero = new StringBuilder();
                    int tempI = i - 1;
                    //si la posicion de i es x, y la anterior a esta es + o -
                    if ((polinomioDesordenado.charAt(i - 1) == '-' || polinomioDesordenado.charAt(i - 1) == '+' || polinomioDesordenado.charAt(i - 1) == 'P')) {
                        tempNumero.append(1);
                    } else {
                        //mientras la posicion anterior no sea - o +, sea mayor a 0 y no sea P
                        while (((polinomioDesordenado.charAt(tempI) != '-' && polinomioDesordenado.charAt(tempI) != '+') && (polinomioDesordenado.charAt(tempI) >= '0') && (polinomioDesordenado.charAt(tempI) != 'P'))) {
                            tempNumero.append(polinomioDesordenado.charAt(tempI));
                            tempI--;
                        }
                    }
                    tempNumero.reverse();
                    //si el caracter anterior es un - o +
                    if (polinomioDesordenado.charAt(tempI) == '-' || polinomioDesordenado.charAt(tempI) == '+') {
                        tempNumero.insert(0, polinomioDesordenado.charAt(tempI));
                    }
                    //hasta aqui se saca el numero, opr si es 4556878524...

                    //a partir de aqui se hace lo que esta despues del ^
                    int tempI2 = i + 2;
                    StringBuilder elevadoA = new StringBuilder();
                    //si el caracter siguiente a i es un ^
                    if (polinomioDesordenado.charAt(i + 1) == '^') {
                        //mientras sea un numero
                        while (polinomioDesordenado.charAt(tempI2) <= '9' && polinomioDesordenado.charAt(tempI2) >= '0') {
                            elevadoA.append(polinomioDesordenado.charAt(tempI2));
                            tempI2++;
                        }
                    } else {
                        String numero = tempNumero.toString();
                        int numeroX = Integer.parseInt(numero);
                        //este if comprueba si ya existe ese grado
                        if (unNomio.containsKey(1)) {
                            unNomio.put(1, unNomio.get(1) + numeroX);
                        } else {
                            unNomio.put(1, numeroX);
                        }
                        continue;
                    }
                    String numero = tempNumero.toString();
                    String grado = elevadoA.toString();
                    int numeroX = Integer.parseInt(numero);
                    int gradoElevado = Integer.parseInt(grado);
                    //comprobar si existe el grado
                    if (unNomio.containsKey(gradoElevado)) {
                        unNomio.put(gradoElevado, unNomio.get(gradoElevado) + numeroX);
                    } else {
                        unNomio.put(gradoElevado, numeroX);
                    }
                }
                //si la i es un numero
            } else if ((polinomioDesordenado.charAt(i) >= '0') && (polinomioDesordenado.charAt(i) <= '9') ) {
                //comprobar aqui si es un numero suelto, seria grado 0
                int temp = i;
                int charAnterior = temp - 1;
                int charSiguiente = temp + 1;
                StringBuilder numeroSuelto = new StringBuilder();
                //mientras el caracter en i sea un numero y el anterior no sea un ^
                if ((polinomioDesordenado.charAt(charAnterior) >= '0') && (polinomioDesordenado.charAt(charAnterior) <= '9') && (polinomioDesordenado.charAt(charSiguiente) == 'P')){
                    continue;
                }
                while ((polinomioDesordenado.charAt(temp) >= '0') && (polinomioDesordenado.charAt(temp) <= '9') && (polinomioDesordenado.charAt(i - 1) != '^') ) {
                    //si el siguiente no es x
                    if (polinomioDesordenado.charAt(charSiguiente) != 'x') {
                        numeroSuelto.append((polinomioDesordenado.charAt(temp)));
                        temp++;
                        charAnterior++;
                        charSiguiente++;
                        //si el siguiente es P, -,+  y no es x, si se cumple se rompe el bucle
                    } else if (((polinomioDesordenado.charAt(charSiguiente) == 'P') || (polinomioDesordenado.charAt(charSiguiente) == '-') ||
                            (polinomioDesordenado.charAt(charSiguiente) == '+')) && ((polinomioDesordenado.charAt(charSiguiente) != 'x'))) {
                        numeroSuelto.append((polinomioDesordenado.charAt(temp)));
                        break;
                        //si el siguiente es x o ^
                    } else if ((polinomioDesordenado.charAt(charSiguiente) == 'x') || (polinomioDesordenado.charAt(charAnterior) == '^')) {
                        temp = i;
                        break;
                    }
                }
                //si  el 'caracter actual' es p, + o - (lo que significa que no hay mas polinomio)
                if (polinomioDesordenado.charAt(temp) == 'P' || polinomioDesordenado.charAt(temp) == '+' || polinomioDesordenado.charAt(temp) == '-' ) {

                    int numero = Integer.parseInt(numeroSuelto.toString());
                    //y se mete al hashmap, pero este if comprueba que el numero sea negativo o positivo
                    if (polinomioDesordenado.charAt(i - 1) == '-') {
                        numero = numero * -1;
                    }
                    if (unNomio.containsKey(0)) {
                        unNomio.put(0, unNomio.get(0) + numero);
                    } else {
                        unNomio.put(0, numero);
                    }
                }
            }
        }
        //hasta aqui se llenaba el hashmap<key = grado, value = numero>, en principio, tambien se simplifica

        ArrayList<Integer> lista = new ArrayList<>();
        //se llena una lista con todas las keys(que son los grados) para encontrar la mayor.
        for (int key : unNomio.keySet()) {
            lista.add(key);
        }
        int longitudArray = Collections.max(lista);
        float[] arraycito = new float[longitudArray + 1];
        //se crea y rellena el array de 0 a partir del numero mas alto de la lista
        for (int i = 0; i < arraycito.length; i++) {
            arraycito[i] = 0;
        }
        //se cambian los valores del array por aquellas keys del map, y se pone el vlaor en la posicion del array. pj. array[posicionkey] = valor de la key
        for (int key : unNomio.keySet()) {
            float valor = unNomio.get(key);
            arraycito[key] = valor;
        }
        float[] darleLaVuelta = new float[longitudArray + 1];
        int temp = 0;
        //hay que darle la vuelta al array, porque estan los numeros donde no toca.
        for (int i = arraycito.length - 1; i >= 0; i--) {
            darleLaVuelta[temp] = arraycito[i];
            temp++;
        }
        setArray(darleLaVuelta);
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polinomial add(Polinomial p) {
        float[] primerPolinom = this.array;
        float[] segundoPolinom = p.array;
        ArrayList<Integer> polinom1 = new ArrayList<>();
        ArrayList<Integer> polinom2 = new ArrayList<>();
        for (int i = 0; i < primerPolinom.length; i++) {
            polinom1.add((int)primerPolinom[i]);
        }
        for (int i = 0; i < segundoPolinom.length; i++) {
            polinom2.add((int)segundoPolinom[i]);
        }

        if (primerPolinom.length > segundoPolinom.length){ //por si es mas grande, hay que sumar en columnas bien ordenadas
            while(polinom1.size() > polinom2.size()){
                polinom2.add(0,0);
            }
        } else if(primerPolinom.length < segundoPolinom.length) {
            while(polinom1.size() < polinom2.size()){
                polinom1.add(0,0);
            }
        }
        ArrayList<Integer> sumas = new ArrayList<>();
        for (int i = 0; i < polinom1.size(); i++) {
            sumas.add(polinom1.get(i) + polinom2.get(i));
        }
        //si hay 0 delante, no nos interesa que se añadan
        while (sumas.get(0) == 0){
            sumas.remove(0);
        }
        float[] resultado = new float[sumas.size()];
        for (int i = 0; i < sumas.size(); i++) {
            resultado[i] = sumas.get(i);
        }
        return new Polinomial(resultado);
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polinomial mult(Polinomial p2) {

        float[] primerPolinom = this.array;
        float[] segundoPolinom = p2.array;
        if ((primerPolinom[0] == 0 && primerPolinom.length == 1) || segundoPolinom[0] == 0 && segundoPolinom.length == 1){
            return new Polinomial("0");
        }
        ArrayList<Integer> polinom1 = new ArrayList<>();
        ArrayList<Integer> polinom2 = new ArrayList<>();
        for (int i = 0; i < primerPolinom.length; i++) {
            polinom1.add((int)primerPolinom[i]);
        }
        for (int i = 0; i < segundoPolinom.length; i++) {
            polinom2.add((int)segundoPolinom[i]);
        }
        //para no dejar huecos en blanco en el array
        if (primerPolinom.length > segundoPolinom.length){
            while(polinom1.size() > polinom2.size()){
                polinom2.add(0,0);
            }
        } else if(primerPolinom.length < segundoPolinom.length) {
            while(polinom1.size() < polinom2.size()){
                polinom1.add(0,0);
            }
        }
        //un nuevo array para darle la vuelta, del tamaño con la suma de los dos arrays-1
        int longRes = (polinom1.size()-1) + (polinom2.size());
        int[] alReves = new int[longRes];
        for (int i = 0; i < alReves.length; i++) {
            alReves[i] = 0;
        }
        //estan al reves:
        Collections.reverse(polinom1);
        Collections.reverse(polinom2);
        for (int i = 0; i < polinom2.size(); i++) {
            for (int j = 0; j < polinom1.size(); j++) {
                //resultado de la multiplicacion:
                int mult = polinom2.get(i) * polinom1.get(j);
                //posicion donde se coloca=suma de los grados
                int posicion = i+j;
                int ocupado = alReves[posicion];
                //igual que el hashmap, si hay algo ahi, se suma
                alReves[posicion] = ocupado+mult;

            }
        }
        //el resultado esta colocado al reves, se añade a la lista para darle la vuelta.
        ArrayList<Integer> resultado = new ArrayList<>();
        for (int i = 0; i < alReves.length; i++) {
            resultado.add(alReves[i]);
        }

        Collections.reverse(resultado);
        //si quedan 0 al principio no nos interesa
        while (resultado.get(0) == 0){
            resultado.remove(0);
        }
        //se pasa lo que hay en la lista a un array final para devolver el resultado.
        float[] resultadoFinal = new float[resultado.size()];
        for (int i = 0; i < resultado.size(); i++) {
            resultadoFinal[i] = resultado.get(i);
        }
        return new Polinomial(resultadoFinal);
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polinomial[] div(Polinomial p2) {
        return null;
    }


    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polinomial p = (Polinomial)o;
        if (this.array.length==p.array.length){
            for (int i = 0; i <this.array.length ; i++) {
                if (this.array[i]!= p.array[i]){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }
    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        float[] elArray = getArray();
        int sizeArray = elArray.length;
        StringBuilder resultado = new StringBuilder();
        int temp = sizeArray;
        int contarCeros = 0;
        //los espacios entre los polinomios y los simbolos se añaden mayormente al final de la frase
        for (int i = 0; i < sizeArray; i++) {
            //solo un numero
            if (sizeArray == 1 || i == sizeArray - 1) {
                if (sizeArray == 1) {
                    resultado.append((int) array[i]);
                } else if (array[i] < 0) { //numero sin x negativo
                    resultado.append("- " + ((int) array[i] * -1));
                } else if (array[i] > 0) { //numero sin x positivo
                    resultado.append("+ " + (int) array[i]);
                } else if (array[i] == 0) { //el ultimo numero es 0
                    contarCeros++;
                }
                //este if no hace nada de nada (importante)
            } else if (array[i] == 0) {
                contarCeros++;

                //x sin numero pj. x^9
            } else if (array[i] == 1 || array[i] == -1) {
                if (array[i] == -1) { //negativo
                    if (temp <= 2) {
                        resultado.append("-x ");
                    } else {
                        if (i == 0){
                            resultado.append("-x" + "^" + (temp - 1) + " ");
                        } else {
                            resultado.append("- x" + "^" + (temp - 1) + " ");
                        }
                    }
                } else { //positivo
                    if (temp <= 2) {
                        if(i == 1){
                            resultado.append("+ x");
                        } else {
                            resultado.append("x ");
                        }

                    } else {
                        resultado.append("x" + "^" + (temp - 1) + " ");
                    }
                }
                //x sin elevar a nada pj. 25x
            } else if (i == array.length - 2) {
                if (array[i] > 0) { //numero positivo
                    if (i == 0 || array[i-1] == '0') { //por si es el primer digito
                        resultado.append((int) array[i] + "x ");
                    } else {
                        resultado.append("+ " + (int) array[i] + "x ");
                    }
                } else if (array[i] < 0) { //numero negativo
                    resultado.append("- " + ((int) array[i] * -1) + "x ");
                }
                //si el numero es negativo pj. -25x^8
            } else if (array[i] < 0) {
                if(i != 0){ //no es el primer numero
                    resultado.append("- " + ((int) array[i] * -1) + "x^" + (temp - 1) + " ");
                } else {
                    resultado.append("-" + ((int) array[i] * -1) + "x^" + (temp - 1) + " ");
                }
                //si el numero es positivo pj. 28x^4
            } else if (array[i] > 0) {
                if (i != 0) { //no es le primer numero
                    resultado.append("+ " + (int) array[i] + "x^" + (temp - 1) + " ");
                } else {
                    resultado.append((int) array[i] + "x^" + (temp - 1) + " ");
                }
            }
            temp--;
        }
        //por si solo hay ceros
        if (contarCeros == sizeArray) {
            return "0";
        }
        //el trim quita los espacios al incio y final si es que los hay
        return resultado.toString().trim();
    }
}