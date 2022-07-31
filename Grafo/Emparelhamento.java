package grafo;
import java.util.Vector;

import grafo.Vertice;
import grafo.Aresta;

public class Emparelhamento
{//Início da Classe

  Vector <Aresta> arestas;
  Vector <Vertice> vertices;

 
  public Emparelhamento()
  {
    arestas = new Vector <Aresta> ();
    vertices = new Vector <Vertice> ();
  }
  
  public Emparelhamento(Emparelhamento e)
  {
    this.arestas = new Vector <Aresta>();
    this.vertices = new Vector <Vertice>();

    for(int i=0;i<e.arestas.size();i++)
    {
      Aresta a = new Aresta(e.arestas.elementAt(i)); 
      this.arestas.add(a);
    }

    for(int i=0;i<e.vertices.size();i++)
    {
      Vertice v = new Vertice(e.vertices.elementAt(i));
      this.vertices.add(v);
    }
  }


  public void addVertice(int v)
  {
    Vertice v1= new Vertice(v);
    vertices.add(v1);

    
    for(int i=vertices.size()-1;i>0;i--)
    {
       if(vertices.elementAt(i).id< vertices.elementAt(i-1).id)
       {
         Vertice aux = vertices.elementAt(i-1);
         vertices.setElementAt(vertices.elementAt(i),i-1);
         vertices.setElementAt(aux,i);
 
       }
    }

  }


  public void addAresta(Aresta a)
  {
    //arestas.add(a);
    addArestaInterno(a);
    addVertice(a.vertice1);
    addVertice(a.vertice2);
    
    
  }



  public void addArestaInterno(Aresta a)
  {
    arestas.add(a);

    
    for(int i=arestas.size()-1;i>0;i--)
    {
       if(arestas.elementAt(i).compareTo( arestas.elementAt(i-1))<0 )
       {
         Aresta aux = arestas.elementAt(i-1);
         arestas.setElementAt(arestas.elementAt(i),i-1);
         arestas.setElementAt(aux,i);
 
       }
    }

  }


 
  public boolean estaPresente( int v)
  {
     //o vetor de vértices é mantido ordenado. A busca pode ser feita por busca binária
     
    int ini =0;
    int fim=vertices.size()-1;

    while(ini<=fim)
    {
      int meio = (ini+fim)/2;
      if(vertices.elementAt(meio).id==v)
        return true;
      else if(vertices.elementAt(meio).id>v)
        fim = meio-1;
      else   
        ini = meio+1;
    }



    return false;   

  }


  public int getSmallestVertice()
  {
    if(vertices.size()==0) 
      return -1;
    return vertices.elementAt(0).id;
  }


 public String toString()
 {
    String res="";
    for(int i=0;i<arestas.size();i++)
    {
      res+=arestas.elementAt(i).toString();
      if(i<arestas.size()-1)
        res+=",";
    }

    if(res.equals(""))
      res="Vazio";
    return res;
 }//public String toString()



  public boolean equals(Emparelhamento e)
  {

    
    if(vertices.size() != e.vertices.size() | arestas.size()!= e.arestas.size())
       return false;
    
   
    for(int i=0;i<vertices.size();i++)
    {
      if(!vertices.elementAt(i).equals(e.vertices.elementAt(i)))
        return false;
    }
    
    
    /*for(int i=0;i<arestas.size();i++)
    {
      boolean achou=false;
      for(int j=0;j<e.arestas.size();j++)
      {
         if(arestas.elementAt(i).compareTo(e.arestas.elementAt(j))==0)
         {
              achou = true;
              break;
         }
      }

      if(!achou)      
       return false;
      
    }*/

   for(int i=0;i<arestas.size();i++)
   {
          
      if(arestas.elementAt(i).compareTo(e.arestas.elementAt(i))!=0)
           return false;      
      
   }

   
       return true;
  }


  public boolean emparelhamentoVazio()
  {
     return arestas.size()==0;
  }


}//Fim da Classe



