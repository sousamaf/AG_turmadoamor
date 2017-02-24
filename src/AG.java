import java.util.Random;

public class AG {
	int geracao = 0;
	float carga = 2.0f;
	
	int pop_aux_index = 0;
	
	final int POP_TAM = 20;
	final int POP_GENE = 6;
	
	int[][] POP = new int[POP_TAM][POP_GENE];
	int[][] POP_AUX = new int[POP_TAM][POP_GENE];
	
	float[] fitness = new float[POP_TAM];
	float[] fitness_porcentagem = new float[POP_TAM];
	
	float[] livros = new float[POP_GENE];
	
	void ag()
	{
		livros[0] = 0.3f;
		livros[1] = 1.3f;
		livros[2] = 0.7f;
		livros[3] = 1.1f;
		livros[4] = 0.5f;
		livros[5] = 0.9f;
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

	
	public static void main(String[] args)
	{
		int T = 30;
		AG ag = new AG();
		
		ag.populacao_inicial();
		
		while(ag.geracao < T)
		{
			ag.avaliacao();
			
			ag.geracao++;
		}
	}
}
