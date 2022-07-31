package grafo;
import java.util.Vector;

import grafo.Aresta;
public class Visitor
{//Início da Classe

  //esta classe utiliza o padrão de projeto visitor para fazer a análise dos vertices e seus graus

  public Visitor(){
   grau=new Vector<Integer>();
   id = new Vector<Integer>();
  }//construtor

  Vector <Integer> grau;
  Vector <Integer> id;

  public void visit(Grafo g)
  {
    for(Vertice v: g.vertices)
    {
      int grauAux=0;
      
      for(Aresta a : g.arestas)
      {
        if(v.id==a.vertice1 || v.id == a.vertice2)
          grauAux++;
      }

      grau.add(new Integer(grauAux));
      id.add(new Integer(v.id));
      

    }
  }//método visit


  public String data2String(Grafo g)
  {
    String res = new String();
   
    //ordenando os vértices por seus graus
    //tecnica de ordenação buble sort
    for(int i=0;i<grau.size()-1;i++)
    {
      for(int j=i+1;j<grau.size();j++)
      {
        if(grau.elementAt(i).compareTo(grau.elementAt(j))>0)
        {
          Integer aux = grau.elementAt(i);
          grau.setElementAt(grau.elementAt(j),i);
          grau.setElementAt(aux,j);

          aux = id.elementAt(i);
          id.setElementAt(id.elementAt(j),i);
          id.setElementAt(aux,j);
        }
      }
    }

    res+="ID\tGrau\tArestas do emparelhamento\n";

    for(int i = 0;i<id.size();i++)
    {
      int idAux = id.elementAt(i).intValue();
      res+= idAux+"\t"+grau.elementAt(i)+"\t"+g.vertices.elementAt(idAux).label+"\n";
    }
    

    return res;
  }
  

}//Fim da Classe



