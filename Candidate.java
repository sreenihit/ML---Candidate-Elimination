import java.util.*;
import java.io.*;

public class Candidate{

	public static void main(String args[])
	{

		int m,n;
		List<String[]> G = new ArrayList<String[]>();
		List<String[]> S = new ArrayList<String[]>();
		String[] attributes;
		String attr = new String();
		String testCase[];
	

		try{
			BufferedReader br = new BufferedReader(new FileReader("file.txt"));
	
			// Read in 'n' and 'm'
			attr = br.readLine();
			//Parse n and m
			String num[] = attr.split(" ");
			n = Integer.parseInt(num[0]);
			m = Integer.parseInt(num[1]);

			
			String features[] = new String[m-1];
			String features2[] = new String[m-1];			
			int i;
			for(i =0;i<m-1;i++)
			features[i] = "?";
			G.add(features);


			for(i =0;i<m-1;i++)
			features2[i] = "#";
			S.add(features2);


			// Read in the attributes
			attr = br.readLine();
			//System.out.println(attr);
			attributes = attr.split(" ");
			
			Boolean first = true;
			Boolean nFirst = true;
			// Read in the lines
			while((attr = br.readLine())!=null)
			{
				System.out.println("Input = " + attr);
				testCase = attr.split(" ");

				if(testCase[m-1].equals("yes") || testCase[m-1].equals("Yes"))
				{

					// Generalize S and Prune G
					if(first)
					{
						for(i=0;i<m-1;i++)
						{
							S.get(0)[i] = testCase[i];
						}
						first =  false;

						System.out.print("S = ");					
						for(i=0;i<m-1;i++)
						System.out.print(S.get(0)[i] + " ");
						System.out.print("\n");

						System.out.print("G = ");					
						for(i=0;i<m-1;i++)
						System.out.print(G.get(0)[i] + " ");
						System.out.print("\n\n");		

						continue;
					}
					

					for(i=0;i<m-1;i++)
					{
	
						if(!testCase[i].equals(S.get(0)[i]))
							S.get(0)[i] = "?";
					}


					// Prune G!!
					for(i = 0;i <G.size();i++)
					{
						for(int j = 0;j<m-1;j++)
						{
							if(!(G.get(i)[j].equals("?")) && !testCase[j].equals(G.get(i)[j]))
							{
								String temp[] = G.get(i);
								G.remove(temp);
								break;
							}
						}
					}

					
					System.out.print("S = ");					
					for(i=0;i<m-1;i++)
					System.out.print(S.get(0)[i] + " ");
					System.out.print("\n");
										
					System.out.print("G = ");					
					for(i=0;i<G.size();i++)
					{
						for(int f = 0;f<m-1;f++)
						System.out.print(G.get(i)[f] + " ");
						System.out.print("\n");
					}
					System.out.print("\n\n");

					
				}
				else
				{
					// Specialize G
					if(nFirst)
					{
							
						String del[] = new String[m-1];
						for(i = 0;i<m-1;i++)
						{

							if(!testCase[i].equals(S.get(0)[i]))
							{

								del = G.get(0);
								String temp[] = new String[m-1];

								for(int k = 0;k<m-1;k++)
								temp[k] = "?";
								temp[i] = S.get(0)[i];

								G.add(temp);
							}		
						}					
						
						nFirst = false;
						G.remove(del);

						// Test all G!
						G = Candidate.check(G,m);
	
		

						System.out.print("S = ");					
						for(i=0;i<m-1;i++)
						System.out.print(S.get(0)[i] + " ");
						System.out.print("\n");
										
						System.out.print("G = ");					
						for(i=0;i<G.size();i++)
						{
							for(int f = 0;f<m-1;f++)
							System.out.print(G.get(i)[f] + " ");

						}
						System.out.print("\n\n");
						continue;

					}	
					else
					{
						int ss = 0;
						int size = G.size();
						for(i=0;i<size;i++)
						{
							String temp[] = G.get(i);
							Boolean del = false;
							for(int k = 0;k<m-1;k++)
							{
								if(G.get(i)[k].equals(testCase[k]))
								{
									del = true;						
									for(int h = 0;h<m-1;h++)
									{
										String vector[] = new String[m-1];
										if(! ((S.get(0)[h]).equals("?")) && !(S.get(0)[h].equals(testCase[k])) )	
										{
											for(int s = 0;s<m-1;s++)
											{
												vector[s] = temp[s];	
											}
											vector[h] = S.get(0)[h];

											G.add(vector);


										}
									}
								}
							}
						
							if(del)
							{
								G.remove(temp);


							        G = Candidate.check(G,m);
								del = false;
							}
					
						}
					}


					System.out.print("No, S = ");					
					for(i=0;i<m-1;i++)
					System.out.print(S.get(0)[i] + " ");
					System.out.print("\n");
										
					System.out.print("G = ");					
					for(i=0;i<G.size();i++)
					{
						for(int f = 0;f<m-1;f++)
						System.out.print(G.get(i)[f] + " ");
						System.out.print("\n");
					}
					System.out.print("\n\n");

				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error" + e.toString());
		}
	
	
	}


	static List<String[]> check(List<String[]> G, int m)
	{
		List<String[]> tempList = new ArrayList<String[]>();
		int count = 0;
		int size = 0;
		int num = 0;

		if(G.size()==1)
		return G;

		for(int i = 0;i<G.size();i++)
		{
			int temp = 0;

			for(int k = 0; k<m-1;k++)
			{
				if( G.get(i)[k].equals("?") )
				temp++;
			}

			if(temp>count)
				count = temp;
		}
				
		for(int i = 0;i<G.size();i++)
		{
			int temp = 0;
			for(int k = 0; k<m-1;k++)
			{
				if( G.get(i)[k].equals("?") )
				temp++;
			}

			if(temp==count)
			{
				num++;
			}
		}

		if(num<G.size())
			for(int i = 0;i<G.size();i++)
			{
				int temp = 0;
				for(int k = 0; k<m-1;k++)
				{
					if( G.get(i)[k].equals("?") )
					temp++;
				}

				if(temp==count)
				{
					tempList.add(G.get(i));
					size++;
				}
			}
		else
			return G;

		//System.out.println("Size = " + size);
		
		for(int i=0;i<tempList.size();i++)
			G.remove(tempList.get(i));

		return G;
		

	}	

}
