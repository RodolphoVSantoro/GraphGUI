package grafo;
public class Aresta implements Comparable
{//Início da Classe

  
  public int vertice1;
  public int vertice2;
  public int id;



  public Aresta(){  }

  public Aresta(Aresta a)
  {
    this.vertice1 = a.vertice1;
    this.vertice2 = a.vertice2;
    this.id = a.id;
  }
 
  public boolean incidente(int v)
  {
    return v==vertice1 || v==vertice2;
  }

  public int getGreatestVertice()
  {
    return (vertice1>vertice2)?vertice1:vertice2;
  }
  
  public int compareTo(Aresta a)
  {
    if(vertice1==a.vertice1 && vertice2==a.vertice2)
      return 0;
    else
    {
      if(vertice1 != a.vertice1)
      {
        if(vertice1>a.vertice1)
          return 1;
        else
          return -1;
      }
      else
      {
        if(vertice2> a.vertice2)
           return 1;
        else
          return -1;
      }
    }
  }

  public int compareTo(Object a)
  {
    return this.compareTo((Aresta) a );
  }

  public String toString()
  {
    return vertice1+"-"+vertice2;
  }


}//Fim da Classe



