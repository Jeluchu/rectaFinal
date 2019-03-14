# CONSTRAINT LAYOUT

### ¿POR QUÉ ES MEJOR?
Este layout nos permite crear interfaces simplificando el anidamiento entre los distintos componentes de los que disponemos, de esta forma, podemos hacer diseños más complejos de una forma más eficaz y sencilla. Es parecido al RelativeLayout, puesto que nos permite establecer relaciones entre los elementos, de este modo es mucho más flexible que el resto, siendo así un layout más responsive en todas las pantallas.

Este layout, tiene la opción también de un modo "Autoconnect",  el cuál conectará todos los elementos de forma automática, una opción muy útil aunque siempre es aconsejable hacerlo de forma manual. Cada vez que añadamos una vista al layout se nos permitirá crear dos o más restricciones para cada uno de los elementos, para asignarlo al espacio donde nosotros la hemos soltado.

Por ejemplo:
````
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"
````
De este modo el  contenido que tengamos con estas restricciones se centrará en la pantalla, si quisieramos enlazarlo a un elemento habría que poner el "id" del mismo para enlazarlo a él (Top, Bottom, Right, Left).
