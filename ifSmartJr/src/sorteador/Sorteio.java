package sorteador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;
import java.util.Calendar;

public class Sorteio {
	
	private Random random = new Random();
	
	// Método que realiza o sorteio
	
	public String sortear(ArrayList <String> listaPro) {
		
		TelaLog telaLog = new TelaLog();
		
		// Embaralhando a ordem de todos os índices da lista
		Collections.shuffle(listaPro);
		
		int tamanhoLista = listaPro.size();
		
		// Sorteando o vencedor
		int vencedor = this.retornarNumeroSorteado(1,tamanhoLista);
		String dadosVencedor = listaPro.get(vencedor);
		
		String dadosSegundoLugar;
		int segundoLugar;
		
		int terceiroLugar;
		String dadosTerceiroLugar;
		
		// Sorteando os demais e os re-sorteando caso iguais
		
		do {
			segundoLugar = this.retornarNumeroSorteado(1,tamanhoLista);
			dadosSegundoLugar = listaPro.get(segundoLugar);
		} while ((segundoLugar == vencedor) || (dadosSegundoLugar.equals(dadosVencedor)));
		
		do {
			terceiroLugar = this.retornarNumeroSorteado(1,tamanhoLista);
			dadosTerceiroLugar = listaPro.get(terceiroLugar);
		} while ((terceiroLugar == vencedor) || (terceiroLugar == segundoLugar)
				|| (dadosTerceiroLugar.equals(dadosVencedor)) || (dadosTerceiroLugar.equals(dadosSegundoLugar)));
		
		// Processando os dados dos vencedores
		
		Arquivo arquivo = new Arquivo();

		String nomeVencedor = dadosVencedor.substring(0,dadosVencedor.indexOf(","));
		String CPFVencedor = arquivo.mascararCPF(dadosVencedor.substring(dadosVencedor.indexOf(",")+2));
		
		String nomeSegundoLugar = dadosSegundoLugar.substring(0,dadosSegundoLugar.indexOf(","));
		String CPFSegundoLugar = arquivo.mascararCPF(dadosSegundoLugar.substring(dadosSegundoLugar.indexOf(",")+2));
	
		String nomeTerceiroLugar = dadosTerceiroLugar.substring(0,dadosTerceiroLugar.indexOf(","));
		String CPFTerceiroLugar = arquivo.mascararCPF(dadosTerceiroLugar.substring(dadosTerceiroLugar.indexOf(",")+2));
		
		String logVencedor = "VENCEDOR: "+nomeVencedor+", CPF: "+CPFVencedor;
		String logSegundoLugar = "SEGUNDO LUGAR: "+nomeSegundoLugar+", CPF: "+CPFSegundoLugar;
		String logTerceiroLugar = "TERCEIRO LUGAR: "+nomeTerceiroLugar+", CPF: "+CPFTerceiroLugar;
		telaLog.info("Premiação Realizada!","Vencedor(a) "+nomeVencedor+"!\nParabéns!");
		telaLog.info("Demais colocados", "Segundo lugar: "+nomeSegundoLugar+"\n"+
				"Terceiro lugar: "+nomeTerceiroLugar);
		
		// Salvando os dados dos vencedores
		
		String diretorio = arquivo.getDiretorioAtual()+"\\Vencedores\\";
		File file = new File(diretorio);
		file.mkdir();
		
		try {
			FileWriter arquivoVencedores = new FileWriter(diretorio+"vencedores.txt");
			PrintWriter listaVencedores = new PrintWriter(arquivoVencedores);
			listaVencedores.printf("DATA E HORA DA PREMIAÇÃO: "+this.retornarDataHora()+"\n\n");
			listaVencedores.printf(logVencedor+"\n"+logSegundoLugar+"\n"+logTerceiroLugar+"\n\n");
			listaVencedores.printf("© 2021 - Desenvolvido pela ifSmartJr.");
			listaVencedores.close();
		} catch (IOException e) {
			return null;
		}
		return logVencedor+"\n"+logSegundoLugar+"\n"+logTerceiroLugar;
	}
	
	// Método que retorna o número aleatório no espectro escolhido
	
	public int retornarNumeroSorteado(int minimo,int maximo) {
		
		int numeroSorteado = (minimo + this.random.nextInt(maximo+1-minimo)) - 1;
		return numeroSorteado;
		
	}
	
	// Retorna em uma String a data e a hora correspondente
	
	public String retornarDataHora() {
		Calendar dataHora = Calendar.getInstance();
		int ano, mes, dia, hora, minuto, segundo;
		ano = dataHora.get(Calendar.YEAR);
		mes = dataHora.get(Calendar.MONTH) + 1;
		dia = dataHora.get(Calendar.DAY_OF_MONTH);
		hora = dataHora.get(Calendar.HOUR_OF_DAY);
		minuto = dataHora.get(Calendar.MINUTE);
		segundo = dataHora.get(Calendar.SECOND);
		return String.format("%02d/%02d/%04d - %02d:%02d:%02d",dia,mes,ano,hora,minuto,segundo);
	}

}