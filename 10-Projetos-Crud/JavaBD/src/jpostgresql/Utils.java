package jpostgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Utils {
	
	static Scanner sc = new Scanner(System.in);
	
	public static Connection conectar()
    {
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "postgre");
		props.setProperty("ssl", "false");
		String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/postgresql";
		
		try {
			return DriverManager.getConnection(URL_SERVIDOR, props);
		}catch(Exception e) {
			e.printStackTrace();
			if(e instanceof ClassNotFoundException) {
				System.err.println("Verifique o driver de conexão.");
			}else {
				System.err.println("Verifique se o servidor está ativo.");
			}
			System.exit(-42);
			return null;
		}
	}
	
	public static void desconectar(Connection conn)
    {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void listar()
    {
		String BUSCAR_TODOS = "SELECT * FROM produtos";
		
		try {
			Connection conn = conectar();
			PreparedStatement produtos = conn.prepareStatement(
					BUSCAR_TODOS,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY
					
			);
			
			ResultSet res = produtos.executeQuery();
			
			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.println("Listando produtos...");
				System.out.println("--------------------");
				while(res.next()) {
					System.out.println("ID: "  + res.getInt(1));
					System.out.println("Produto: "  + res.getString(2));
					System.out.println("Preço: "  + res.getFloat(3));
					System.out.println("Estoque: "  + res.getInt(4));
					System.out.println("--------------------");
				}
			}else {
				System.out.println("Não existem produtos cadastrados.");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos os produtos.");
			System.exit(-42);
		}
	}
	
	public static void inserir()
    {
		System.out.print("Informe o nome do produto: ");
        sc.nextLine();
		String nome = sc.nextLine();
		
		System.out.print("Informe o preço: ");
		float preco = sc.nextFloat();
		
		System.out.print("Informe a quantidade em estoque: ");
		int estoque = sc.nextInt();
		
		String INSERIR = "INSERT INTO produtos (nome, preco, estoque) VALUES (?, ?, ?)";
		
		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);
			
			salvar.setString(1, nome);
			salvar.setFloat(2,  preco);
			salvar.setInt(3,  estoque);
			
			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("O produto " + nome + " foi inserido com sucesso.");
		}
        catch(Exception e) {
			e.printStackTrace();
			System.err.println("Erro salvando produto.");
			System.exit(-42);
		}
	}
	
	public static void atualizar()
    {
		System.out.print("Informe o código do produto: ");
		int id = sc.nextInt();
		
		String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(
					BUSCAR_POR_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY
			);
			produto.setInt(1,id);
			ResultSet res = produto.executeQuery();
			
			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.print("Informe o nome do produto: ");
                sc.nextLine();
				String nome = sc.nextLine();
				
				System.out.print("Informe o preço do produto: ");
				float preco = sc.nextFloat();
				
				System.out.print("Informe a quantidade em estoque: ");
				int estoque = sc.nextInt();
				
				String ATUALIZAR = "UPDATE produtos SET nome=?, preco=?, estoque=? WHERE id=?";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
				
				upd.setString(1, nome);
				upd.setFloat(2,  preco);
				upd.setInt(3,  estoque);
				upd.setInt(4,  id);
				
				upd.executeUpdate();
				upd.close();
				desconectar(conn);
				System.out.println("O produto " + nome + " foi atualizado com sucesso.");
			}else {
				System.out.println("Não existe produto com o id informado.");
			}
			
		}
        catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível atualizar o produto.");
			System.exit(-42);
		}
	}
	
	public static void deletar()
    {
		String DELETAR = "DELETE FROM produtos WHERE id=?";
		String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id=?";
		
		System.out.print("Informe o código do produto: ");
		int id = sc.nextInt();
		
		try {
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(
					BUSCAR_POR_ID,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY
			);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();
			
			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, id);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				System.out.println("O produto foi deletado com sucesso.");
			}else {
				System.out.println("Não existe produto com o id informado.");
			}
			
		}
        catch(Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar produto");
			System.exit(-42);
		}
		
	}
	
	public static void menu()
    {
        try {
            while(true) {
                System.out.println("==================Gerenciamento de Produtos===============");
                System.out.println("Selecione uma opção: ");
                System.out.println("1 - Listar produtos.");
                System.out.println("2 - Inserir produtos.");
                System.out.println("3 - Atualizar produtos.");
                System.out.println("4 - Deletar produtos.");
                System.out.print("5 - Sair.\n:_");
                
                int opcao = sc.nextInt();

                if(opcao == 1) {
                    listar();
                }else if(opcao == 2) {
                    inserir();
                }else if(opcao == 3) {
                    atualizar();
                }else if(opcao == 4) {
                    deletar();
                }else if(opcao == 5) {
                    System.out.println("Saindo...");
                    break;
                }else {
                    System.out.println("Opção inválida.");
                }
            }
        }
        catch(Exception e) {
            System.out.println("Opção Inválida.");
        }
	}
}