package grafo;
public class Vertice
{//Início da Classe
  public int id;
  public String label;

  public Vertice(){}

  public Vertice(int i)
  {
    id = i;
  }

  public Vertice(Vertice v)// copy constructor
  {
    this.id = v.id;
  }

  public boolean equals(Vertice v)
  {
    return v.id == id;
  }

  public String toString()
  {
    return id+":"+label;
  }

}//Fim da Classe



