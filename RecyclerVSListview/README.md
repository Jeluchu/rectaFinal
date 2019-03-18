# RECYCLER VIEW vs LISTVIEW

El "RecyclerView" es la versión sucesora y mejorada del ListView, ya que está más optimizado, puesto que va refrescando las vistas de los elementos que no se visualizan en la pantalla para utilizarlos en los próximos elementos.
Esto es muy útil para cuándo tenemos listas que son muy extensas. Se usa de forma muy similar al ListView, puesto que necesitamos una fuente de datos, y un adaptador, encargado de leer, procesar e inflar la lista.

Una de las diferencias es que el ViewHolder, es obligatorio en el RecyclerView para crear (inflar el diseño y encontrar las vista) y actualizar las vistas. Se usan dos métodos: **onCreateViewHolder() y onBindViewHolder()**.

Con el LayoutManager podemos diseñar las vistas de las filas; es debido a esto que el RecyclerView no necesita saber como colocar la vista de las filas. De esta forma la clase nos dará a elegir la forma en la que queremos mostrar 
las vistas de la fina y como desplazarse por las mismas. Con el uso del **ListView** solo podíamos crear una lista con desplazamiento vertical, siendo menos flexible que el **RecyclerView**.

