import java.util.Random;

public class AG {
	int T = 1; // numero de geracoes populacionais do criterio de parada
	final int TAXA_MUTACAO = 5;
	
	int geracao = 0;
	
	float carga = 2.0f;
	
	int pop_aux_index = 0;
	
	final int POP_TAM = 20;
	final int POP_GENE = 6;
	
	int[][] POP = new int[POP_TAM][POP_GENE];
	int[][] POP_AUX = new int[POP_TAM][POP_GENE];
	
	float[] fitness = new float[POP_TAM];
	float[] fitness_porcentagem = new float[POP_TAM];
	
	public float[] livros = new float[POP_GENE];
	
	public AG()
	{
		int i;
		
		this.livros[0] = 0.3f;
		this.livros[1] = 1.3f;
		this.livros[2] = 0.7f;
		this.livros[3] = 1.1f;
		this.livros[4] = 0.5f;
		this.livros[5] = 0.9f;
		
		for(i = 0; i < POP_TAM; i++)
		{
			this.fitness[i] = 0.0f;
			this.fitness_porcentagem[i] = 0.0f;
		}
	}
	
	void populacao_inicial()
	{
		int i, g;
		for(i = 0; i < POP_TAM; i++)
			for(g = 0; g < POP_GENE; g++)
				POP[i][g] = new Random().nextInt(2);
	}
	
	void cruzamento_simples_um_ponto(int pai1, int pai2)
	{
		int g;
		for(g = 0; g < POP_GENE; g++)
		{
			if( g < POP_GENE/2)
			{
				POP_AUX[pop_aux_index][g]	= POP[pai1][g];
				POP_AUX[pop_aux_index+1][g]	= POP[pai2][g];
			} else
			{
				POP_AUX[pop_aux_index][g]	= POP[pai2][g];
				POP_AUX[pop_aux_index+1][g]	= POP[pai1][g];
			}
		}
		pop_aux_index = pop_aux_index + 2;
	}
	
	void mutacao()
	{
		int quem = new Random().nextInt(POP_TAM);
		int gene = new Random().nextInt(POP_GENE);
		
		POP_AUX[quem][gene] = (POP_AUX[quem][gene] == 0)? 1: 0;
		
	}
	
	void substituicao()
	{
		int i, g;
		for(i = 0; i < POP_TAM; i++)
			for(g = 0; g < POP_GENE; g++)
				POP[i][g] = POP_AUX[i][g];
	
	}
	
	void avaliacao()
	{
		int i, g;
		for(i = 0; i < POP_TAM; i++)
		{
			for(g = 0; g < POP_GENE; g++)
			{
				fitness[i] += livros[g] * POP[i][g];
			}
			if(fitness[i] > carga)
			{
				fitness[i] = 0.0f;
			}
		}
	}
	
	void avaliacao_porcentagem()
	{
		int i;
		float fitness_total = 0.0f;
		
		for(i = 0; i < POP_TAM; i++)
		{
			fitness_total += fitness[i];
		}
		
		for(i = 0; i < POP_TAM; i++)
		{
			fitness_porcentagem[i] = (fitness[i] * 100)/fitness_total;
		}
	}
	
	int roleta()
	{
		float soma = 0.0f;
		int i = -1;
		int num = new Random().nextInt(100);
		
		while(num > soma)
		{
			i++;
			soma += fitness_porcentagem[i];
		}
		if( i < 0 )
			i = 0;
		return i;
	}
	
	void mostra_pop()
	{
		int i, j;
		for(i = 0; i < POP_TAM; i++)
		{
			System.out.print("Ind." + i + " [ ");
			for(j = 0; j < POP_GENE; j++)
			{
				System.out.print(POP[i][j] + " ");
			}
			System.out.println("]");
		}
	}

	void mostra_pop_aux()
	{
		int i, g;
		for(i = 0; i < pop_aux_index; i++)
		{
			System.out.print("Desc." + i + " [ ");
			for(g = 0; g < POP_GENE; g++)
			{
				System.out.print(POP_AUX[i][g] + " ");
			}
			System.out.println("]");
		}
	}

	void melhor_individuo()
	{
		int i, g, iMelhor;
		float melhor = 0.0f;
		
		melhor = fitness[0];
		iMelhor = 0;
		
		for(i = 1; i < POP_TAM; i++)
		{
			if(fitness[i] > melhor)
			{
				melhor = fitness[i];
				iMelhor = i;
			}
		}

		System.out.print("Melhor: " + " [ ");
		for(g = 0; g < POP_GENE; g++)
		{
			System.out.print(POP[iMelhor][g] + " ");
		}
		System.out.println("] = " + melhor);
		

		
	}

	
	public static void main(String[] args)
	{
		
		int pai1, pai2;
		
		AG ag = new AG();
		
		int tx_mutacao = (ag.POP_TAM * ag.TAXA_MUTACAO)/100;
		
		int i = 0;
		ag.populacao_inicial();
		ag.mostra_pop();

		while(ag.geracao < ag.T)
		{
			ag.avaliacao();
			
			ag.avaliacao_porcentagem();
			
			while(ag.pop_aux_index < ag.POP_TAM)
			{
				pai1 = ag.roleta();
				pai2 = ag.roleta();
				
				while(pai1 == pai2)
					pai2 = ag.roleta();
				
				ag.cruzamento_simples_um_ponto(pai1, pai2);
			}
			
			while(i <= tx_mutacao)
			{
				ag.mutacao();
				i++;
			}
			
			ag.substituicao();
			ag.geracao++;
			ag.pop_aux_index = 0;
		}
	}
}
