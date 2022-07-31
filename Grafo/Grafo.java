package grafo;
import java.util.Vector;

import grafo.Aresta;
import grafo.Emparelhamento;
import grafo.Vertice;

import java.util.LinkedList;
import java.util.Collections;

public class Grafo
{//Início da Classe
 
  public Vector <Aresta> arestas;
  public Vector <Vertice> vertices;

  public Grafo()
  {
    arestas = new Vector <Aresta>();
    vertices = new Vector <Vertice> ();
  }
  
  public int getId(String emp)
  {
    
    for(int i=0;i<vertices.size();i++)
    {
      if(vertices.elementAt(i).label.compareTo(emp)==0)
      {
         return i;
      }
    }
    return -1;
  }
 












  public Vector <Aresta> diferencaSimetrica(Emparelhamento eA, Emparelhamento eB)
  {
    return diferencaConjuntoAresta(uniaoEmparelhamentos(eA,eB),intersecaoEmparelhamentos (eA,eB));
  }//public Vector <Aresta> diferencaSimetrica(Emparelhamento A, Emparelhamento B)
  







  public Vector<Aresta> diferencaConjuntoAresta(Vector <Aresta> c1, Vector <Aresta> c2)
  {
    // Esta função é um caso particular da diferença entre conjuntos. Neste caso c1 é a união entre dois conjuntos A e B e c2 é a interseção entre os conjuntos A e B. Neste caso, c1 é sempre maior que c2
    //como os conjuntos estão ordenados, a diferença pode ser obtida através de uma alteração na operção de intercalação
    //quando os elementos são iguais, descarta-se os dois. quando o elemento de c1 é menor, coloca-se ele no resultado e descarta-se ele
    // quando o elemento de c2 é menor descarta-se  


    Vector <Aresta> res = new Vector <Aresta>();

    //caso Trivial: a interseção é vazia
    if(c2.isEmpty())
    {
      for(Aresta a: c1)
      {
        res.add(a);
      }
      return res;
    }

   
    
    int i=0,j=0; // i marca a posição da areta no conjunto c1, j no c2 
    Aresta cabecaA ;// referência auxiliar
    Aresta cabecaB ;// referência auxiliar

    while(i< c1.size() && j < c2.size()) //operação de intercalação até terminar um dos vetores
    {
       cabecaA = c1.elementAt(i);//esta atualização no começo do laço não é eficiente mas simplifica muito os algoritmos
       cabecaB = c2.elementAt(j);


      if(cabecaA.compareTo(cabecaB)==0)
      {        
        i++;
        j++;
       
      }
      else if(cabecaA.compareTo(cabecaB)<0)
      {
        res.add(new Aresta(cabecaA));
        i++;        
              
      }
      else
      {       
        j++;        
        
      }
      
    }

    while(i< c1.size() )// indo até o fim do vetor de arestas de c1
    {
      cabecaA = c1.elementAt(i);
      res.add(new Aresta(cabecaA));
      i++;        
      
    }

   
    return res;
  
  }//public Vector<Aresta> diferencaConjuntoAresta(Vector <Aresta> c1, Vector <Aresta> c2)






  

  public boolean eConexo(Vector <Aresta> diferencaSimetrica)
  {
    //Esta função recebe como parâmetro uma diferença simétrica e verifica 
    //se o grafo induzido por esta diferença é conexo.
    //Para isso é criado um grafo auxiliar, que é o grafo induzido pela diferença
    //Simétrica e uma execução do algoritmo de Dijkstra sobre este grafo determina
    //se ele é conexo ou não. No final da execução do algoritmo de Dijkstra, caso 
    //exista algum vértice v com distância infinita (representada por -1), o grafo
    //não possui um caminho da origem até v, e portanto não é um grafo conexo

    Vector <Integer> verticesDoConjunto= new Vector();

    //inserindo os vértices das arestas no conjunto
    for( Aresta a: diferencaSimetrica)
    {
       verticesDoConjunto.add(a.vertice1);
       verticesDoConjunto.add(a.vertice2);
    }

    //Com os vértices inseridos no vetor, deve-se remover os vértices repetidos
    //o melhor modo de se fazer isso é ordenar o conjunto para deixar os vértices 
    //repetidos lado a lado

    //ordenando
    Collections.sort(verticesDoConjunto);
    
    //removendo as réplicas
    for(int i=verticesDoConjunto.size()-1; i>0 ;i--)
    {
      if(verticesDoConjunto.elementAt(i)==verticesDoConjunto.elementAt(i-1))
      {
        verticesDoConjunto.removeElementAt(i); 
        
      }
     
    }

    
    //criando a matriz de adjacência que representa o grafo induzido
    int numVer = verticesDoConjunto.size();
    int[][] grafoInduzido = new int[numVer][numVer];

    for(int i=0;i<numVer;i++)
     for(int j=0;j<numVer;j++)
       grafoInduzido[i][j]=-1;//-1 representa que não há ligação

    
    for(Aresta a: diferencaSimetrica)
    {
       int pos1=-1;// pos1 é a posicao do primeiro vertice da aresta
       int pos2=-1;// pos1 é a posicao do primeiro vertice da aresta


       //TODO busca sequencial, pode ser melhorada com busca binária       
       for(int i=0;i<verticesDoConjunto.size();i++)
       {
          if(a.vertice1==verticesDoConjunto.elementAt(i).intValue())
            pos1=i;
       }

       for(int i=0;i<verticesDoConjunto.size();i++)
       {
          if(a.vertice2==verticesDoConjunto.elementAt(i).intValue())
            pos2=i;
       }

       if(pos1<0 || pos2<0 || pos1==pos2)
       {
         System.out.println( "problemas nas posições da matriz pos1="+pos1+" pos2="+pos2);
         System.exit(1);
       }
       

       grafoInduzido[pos1][pos2]=1;//como o grafo não possui pesos, o custo é 1
       grafoInduzido[pos2][pos1]=1;

    }//for(Aresta a: diferencaSimetrica)
    
    //Vetor de distâncias de Dijkstra
    int [] d = new int[numVer];
    //d[0]=0;
    // os inteiros em java iniciam com zero portanto esta inicializa
    //inicialização está aqui só por didática,
   
    for(int i=1;i<numVer;i++)
    {
      d[i]=-1;//-1 representa infinito
    }

    
    boolean[] s = new boolean[numVer];
    //este vetor indica quais os vértices já avaliados

    boolean continueRodando=true;

    //algoritmo de Dijkstra
    while(continueRodando)
    {

      //encontrado o vértice com a menor distância
      int pos=-1;
      int menor=-1;
      for(int i=0;i<d.length;i++)
      {
        if(d[i]>=0 && !s[i])
        {
          if(pos<0 || pos>=0 && d[i]<menor)
          {
            pos=i;
            menor=d[i];
          }
        }
      }

      //neste ponto, encontrar um valor de pos igual a -1 indica grafo desconexo
      if(pos<0)
        return false;
      

      s[pos]=true;//marcando o vértice da posição pos


      for(int i=0;i<numVer;i++)
      {
        if(i!=pos && grafoInduzido[pos][i]>0)
        {
          if(d[i]<0|| d[i]> d[pos]+grafoInduzido[pos][i])
           d[i]= d[pos]+grafoInduzido[pos][i];
        }
      }

       
      boolean aux=false;
      for(int i=0;i<s.length;i++)
      {
        if(!s[i])//equanto houver um vertice nao avaliado, deve-se continuar
        {
          aux=true;
          break;
        }
      }
 
      continueRodando = aux; 

    }


    
    for(int i=0;i<d.length;i++)
      if(d[i]<0)
        return false;//caso algum vertice tenha uma distância infinita, o grafo 
                     //não é conexo

    return true;//se o algoritmo chega neste ponto, todos os vértices possuem
                //distância >=0, e portanto, o grafo é conexo 
  }










  public Vector<Aresta> intersecaoEmparelhamentos(Emparelhamento eA, Emparelhamento eB)
  {

    Vector <Aresta> res = new Vector <Aresta>();

    //caso trivial: um dos emparelhamentos é vazio
    if(eA.emparelhamentoVazio() || eB.emparelhamentoVazio())
    {
      return res;
    } 
   
     
    //para fazer a operação de interseção basta fazer uma adaptação na operação de intercalação entre os dois emparelhamentos, visto que estes estão ordenados.
    //caso os elementos não sejam iguais, descarta-se o menor. Caso eles sejam iguais eles fazem parte da interseção
    int i=0,j=0; // i marca a posição da areta no emparelhamento eA, j no eB  
    Aresta cabecaA ;// referência auxiliar
    Aresta cabecaB ;// referência auxiliar

    while(i< eA.arestas.size() && j <eB.arestas.size()) //operação de intercalação até terminar um dos vetores
    {
      cabecaA = eA.arestas.elementAt(i);//esta atualização no começo do laço não é eficiente mas simplifica muito os algoritmos
      cabecaB = eB.arestas.elementAt(j);

      if(cabecaA.compareTo(cabecaB)==0)
      {
        res.add(new Aresta(cabecaA));
        i++;
        j++;
        
      }
      else if(cabecaA.compareTo(cabecaB)<0)
      {
        
        i++;        
               
      }
      else
      {
        
        j++;        
       
      }
      
    }
    

    return res;
  }//  public Vector<Aresta> intersecaoEmparelhamentos(Emparelhamento eA, Emparelhamento eB)










  public Vector < Vector<Emparelhamento> > montarEmparelhamentos()
  {
     Vector < Vector<Emparelhamento> > resultado = new Vector <Vector <Emparelhamento> >();

     //casos iniciais
     //emparelhamento vazio
     Vector <Emparelhamento> v1 = new Vector<Emparelhamento>();
     Emparelhamento vazio = new Emparelhamento();
     v1.add(vazio);
     resultado.add(v1);

    
     //emparelhamentos de tamanho 1
     Vector <Emparelhamento> v2 = new Vector<Emparelhamento>();
     
     for(int i=0;i< arestas.size();i++)
     {
       Emparelhamento e1 = new Emparelhamento();
       e1.addAresta(new Aresta(arestas.elementAt(i)));

       

       v2.add(e1);

     }
     resultado.add(v2);


     //demais emparelhamentos.
     //for(int i=2;i<arestas.size();i++)//assim funciona para grafos conexos
     for(int i=2;i<=arestas.size();i++)//tentativa para grafos desconexos
     {
       

       Vector < Emparelhamento > en= new Vector <Emparelhamento>();

       for(int j=0;j<resultado.elementAt(i-1).size();j++)//usando os emparelhamentos de tamanho n-1 como base
       {
          Emparelhamento daVez = resultado.elementAt(i-1).elementAt(j);
          
          //criando uma lista com possíveis arestas a serem inseridas
          LinkedList <Aresta> l1=new LinkedList <Aresta> ();
          for(int k=0;k<arestas.size();k++)      
          {
            Aresta a1 = arestas.elementAt(k);
            //System.out.println("processando aresta "+a1);

            if(!daVez.estaPresente(a1.vertice1) && !daVez.estaPresente(a1.vertice2) //eliminando vertices já presentes
            &   (a1.getGreatestVertice()>daVez.getSmallestVertice())  ) //emparelhamentos já tratados 
            {
              
                //System.out.println("adicionou a aresta "+a1+"no emparelhamento"+daVez);
                Aresta a2 = new Aresta(a1);
                l1.add(a2);
              
            }           
          }
         
          if(l1.isEmpty())
            continue;
          else
          {
            while(!l1.isEmpty())
            {
              Emparelhamento novo = new Emparelhamento(daVez);
              Aresta a = l1.removeFirst();
              novo.addAresta(a);

              boolean repetido=false;
              for(int k=0;k<en.size();k++)//desconsiderando os emparelhamentos repetidos
              { 
                 if(en.elementAt(k).equals(novo))
                 {
                   repetido=true;
                   break;
                 }
              }
              if(!repetido)
                en.add(novo);
            }
          }
                
    
       }

       if(en.size()==0)
          break;
       else
         resultado.add(en);
       
     }



     return resultado;
  }//public Vector < Vector<Emparelhamento> > montarEmparelhamentos()











  public Grafo montarGrafoEmparelhamentos()
  {
     Vector< Vector<Emparelhamento> > empar = this.montarEmparelhamentos();


     //System.out.println(empar);

    
     Grafo res = new Grafo();
     int id=0;

    //criando os vértices do grafo
     for(int i=0;i<empar.size(); i++)
     {
       for(int j=0;j<empar.elementAt(i).size();j++)
       {
        
         Vertice novo = new Vertice();
         novo.id = id;
         id++;
         novo.label = empar.elementAt(i).elementAt(j).toString();
         res.vertices.add(novo);
       }
     }


    //criando as arestas do grafo
    //primeiro, criar vértices entre os emparelhamentos de mesmo tamanho
    int idAresta=0;
    for(int i=0;i<empar.size(); i++)//i representa o tamanho dos emparelhamentos
     {
       for(int j=0;j<empar.elementAt(i).size()-1;j++)//j são os emparelhamentos do tamanho i
       {
                 

          for(int k=j+1; k<empar.elementAt(i).size();k++)
          {
            
               
               
              
               if(eConexo(diferencaSimetrica(empar.elementAt(i).elementAt(j),empar.elementAt(i).elementAt(k) ) ) )
               {  
                 String s1= empar.elementAt(i).elementAt(j).toString();
                 String s2= empar.elementAt(i).elementAt(k).toString();
 
                 int id1= res.getId(s1);
                 int id2= res.getId(s2);

                  Aresta a1=new Aresta();
                  a1.id=idAresta;
                  idAresta++;
                  a1.vertice1=id1;
                  a1.vertice2=id2;
                  res.arestas.add(a1);
                 
                    
               }
             

          }
        
                  
       }
     }


     
     //criando arestas entre emparelhamantos de tamanho diferente
     //neste caso só é preciso analisar os emparalhementos com diferença de tamanho 1
     for(int i=0;i<empar.size()-1; i++)//i representa o tamanho dos emparelhamentos
     {
       for(int j=0;j<empar.elementAt(i).size();j++)//j são os emparelhamentos do tamanho i
       {
                 

          for(int k=0; k<empar.elementAt(i+1).size();k++)//k são os emparelhamentos de tamanho i+1
          {
               
               
              
               if(eConexo(diferencaSimetrica(empar.elementAt(i).elementAt(j),empar.elementAt(i+1).elementAt(k) ) ) )
               {  
                 String s1= empar.elementAt(i).elementAt(j).toString();
                 String s2= empar.elementAt(i+1).elementAt(k).toString();
 
                 int id1= res.getId(s1);
                 int id2= res.getId(s2);

                  Aresta a1=new Aresta();
                  a1.id=idAresta;
                  idAresta++;
                  a1.vertice1=id1;
                  a1.vertice2=id2;
                  res.arestas.add(a1);
                 
                    
               }
             

          }
        
                  
       }
     }

     
     return res;
  }






   public String toString()
   {
     String res= new String();
    
     res+="Conjunto de Vértices\n";
     res+="id:Emparelhamento\n";
     for(int i=0;i<this.vertices.size();i++)
     {
        res+=this.vertices.elementAt(i)+"\n";
     }

     res+="\nConjunto de Arestas: ligações são referentes aos ids dos vértices\n";
     for(int i=0;i<this.arestas.size();i++)
     {
        res+= this.arestas.elementAt(i).id+":"+this.arestas.elementAt(i).toString()+"\n";
     }

     res+="\n";
     return res;
   }



   




  public Vector<Aresta> uniaoEmparelhamentos(Emparelhamento eA, Emparelhamento eB)
  {

    Vector <Aresta> res = new Vector <Aresta>();
    //caso trivial um dos emparelhamentos é vazio
    if(eA.emparelhamentoVazio())
    {
      for( Aresta a: eB.arestas)
      {
        res.add(new Aresta(a));
      }
      return res;
    }

     if(eB.emparelhamentoVazio())
    {
      for( Aresta a: eA.arestas)
      {
        res.add(new Aresta(a));
      }
      return res;
    }
    
    //para fazer a operação de união basta fazer a operação de intercalação  entre os dois emparelhamentos, visto que estes estão ordenados.
    int i=0,j=0; // i marca a posição da areta no emparelhamento eA, j no eB  
    Aresta cabecaA ;// referência auxiliar
    Aresta cabecaB ;// referência auxiliar

    while(i< eA.arestas.size() && j <eB.arestas.size()) //operação de intercalação até terminar um dos vetores
    {
      cabecaA = eA.arestas.elementAt(i);//esta atualização no começo do laço não é eficiente mas simplifica muito os algoritmos
      cabecaB = eB.arestas.elementAt(j);
      if(cabecaA.compareTo(cabecaB)==0)
      {
        res.add(new Aresta(cabecaA));
        i++;
        j++;
      
        
      }
      else if(cabecaA.compareTo(cabecaB)<0)
      {
        res.add(new Aresta(cabecaA));
        i++;        
      
      }
      else
      {
        res.add(new Aresta(cabecaB));
        j++;        
      
      }
      
    }

    while(i< eA.arestas.size() )// indo até o fim do vetor de arestas de eA
    {
      cabecaA = eA.arestas.elementAt(i);
      res.add(new Aresta(cabecaA));
      i++;        
      
    }

    while( j <eB.arestas.size())  // indo até o fim do vetor de arestas de eB
    {
      cabecaB = eB.arestas.elementAt(j);
      res.add(new Aresta(cabecaB));
      j++;        
      
    }

    return res;
  }//  public Vector<Aresta> uniaoEmparelhamentos(Emparelhamento eA, Emparelhamento eB)






   

  
}//Fim da Classe



