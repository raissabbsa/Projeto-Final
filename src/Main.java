import controllers.*;
import gui.*;
import models.*;
class Main{
    public static void main(String[] var0){
        Livro livro4=new Livro("nall", "esqueci",2020),livro1=new Livro("noll", "esqueci",2020),livro2=new Livro("A volta dos que num foram", "Jose Chespirito",0),livro3=new Livro("AAAAAAAAAAA", "Eu",1303);
        Aluno aluno1=new Aluno("Eu", "452364","E452364","erererrere"),aluno2=new Aluno("Nao eu", "345654","ererer","iriririr");
        Professor prof1=new Professor("Anastacio", "imecc","kjh@gmail","abafa"),prof2=new Professor("Eugenio", "imekk","grsfvsf@","imecc");
        Biblioteca bib=new Biblioteca();

        bib.cadastrarLivro(livro1);
        bib.cadastrarLivro(livro2);
        bib.cadastrarLivro(livro3);
        bib.cadastrarLivro(livro4);
        bib.cadastrarUsuario(prof1);
        bib.cadastrarUsuario(prof2);
        bib.cadastrarUsuario(aluno1);
        bib.cadastrarUsuario(aluno2);
        new GUI(bib);
    }
}