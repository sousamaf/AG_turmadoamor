import java.util.Random;

public class AG {
	int geracao = 0;
	
	int pop_aux_index = 0;
	
	final int POP_TAM = 20;
	final int POP_GENE = 6;
	
	int[][] POP = new int[POP_TAM][POP_GENE];
	int[][] POP_AUX = new int[POP_TAM][POP_GENE];
	
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
		AG ag = new AG();
		ag.populacao_inicial();
		ag.mostra_pop();
		ag.cruzamento_simples_um_ponto(18, 19);
		ag.mostra_pop_aux();
	}
}
