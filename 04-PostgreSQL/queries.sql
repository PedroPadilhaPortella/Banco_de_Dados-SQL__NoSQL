select nome from atores;

select nome, sobrenome, endereco, telefone from clientes;

select genero from generos;

select * from dvds join filmes on dvds.id_filme = filmes.id;

select f.titulo, g.genero, f.valor from filmes as f join generos as g on f.id_genero = g.id;

select af.id, f.titulo, a.nome, af.personagem from filmes as f, atores as a, atores_filme as af
where af.id_filme = f.id and af.id_atores = a.id;

select titulo from filmes join generos on generos.genero = 'Ação' and generos.id = filmes.id_genero;

select sum(valor) as total from filmes;

select max(valor) as "mais caro" from filmes;

select * from filmes where valor in(select max(valor) from filmes);

select titulo, genero, personagem from filmes, atores_filme, generos, atores where atores.nome = 'Silvester Stalone'
and filmes.id = atores_filme.id_filme and atores.id = atores_filme.id_atores and filmes.id_genero = generos.id;

select titulo, personagem, nome from filmes, atores_filme, generos, atores where generos.genero = 'Ação'
and filmes.id = atores_filme.id_filme and atores.id = atores_filme.id_atores and filmes.id_genero = generos.id;

select clientes.nome, clientes.sobrenome, emprestimos.data, filmes.titulo, genero 
from clientes, generos, emprestimos, filmes, dvds, filmes_emprestimo
where clientes.id = emprestimos.id_cliente and filmes_emprestimo.id_emprestimos = emprestimos.id and
filmes_emprestimo.id_dvd = dvds.id and filmes.id = dvds.id_filme and filmes.id_genero = generos.id;

select clientes.nome, clientes.sobrenome, devolucoes.data as "data de devolucao", filmes.titulo
from clientes, devolucoes, filmes, filmes_devolucao, dvds, emprestimos, filmes_emprestimo
where filmes_devolucao.id_filme_emprestimo = filmes_emprestimo.id and emprestimos.id_cliente = clientes.id and
filmes.id =dvds.id_filme and filmes_emprestimo.id = dvds.id and devolucoes.id_emprestimos = emprestimos.id and
filmes_devolucao.id_devolucao = devolucoes.id;

select clientes.nome, clientes.sobrenome, sum(filmes.valor)
from filmes_devolucao, clientes, filmes, dvds, devolucoes, emprestimos, filmes_emprestimo
where filmes_devolucao.id_filme_emprestimo = filmes_emprestimo.id and filmes.id = dvds.id_filme 
and filmes_emprestimo.id_dvd = dvds.id and clientes.id = emprestimos.id_cliente
and filmes_emprestimo.id_emprestimos = emprestimos.id group by clientes.nome, clientes.sobrenome;

select titulo, valor, quantidade from dvds join filmes on dvds.id_filme = filmes.id;