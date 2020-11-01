CREATE TABLE `generos` (
  `id` int PRIMARY KEY,
  `genero` varchar(255) NOT NULL
);

CREATE TABLE `filmes` (
  `id` int PRIMARY KEY,
  `titulo` varchar(255) NOT NULL,
  `id_genero` int NOT NULL,
  `valor` decimal(8,2) NOT NULL
);

CREATE TABLE `dvds` (
  `id` int PRIMARY KEY,
  `id_filme` int NOT NULL,
  `quantidade` int NOT NULL
);

CREATE TABLE `atores` (
  `id` int PRIMARY KEY,
  `nome` varchar(255) NOT NULL
);

CREATE TABLE `atores_filme` (
  `id` int PRIMARY KEY,
  `personagem` varchar(255) NOT NULL,
  `id_atores` int NOT NULL,
  `id_filme` int NOT NULL
);

CREATE TABLE `clientes` (
  `id` int PRIMARY KEY,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL
);

CREATE TABLE `emprestimos` (
  `id` int PRIMARY KEY,
  `data` datetime NOT NULL,
  `id_cliente` int NOT NULL
);

CREATE TABLE `filmes_emprestimo` (
  `id` int PRIMARY KEY,
  `id_dvd` int NOT NULL,
  `id_emprestimos` int NOT NULL
);

CREATE TABLE `devolucoes` (
  `id` int PRIMARY KEY,
  `id_emprestimos` int NOT NULL,
  `data` datetime NOT NULL
);

CREATE TABLE `filmes_devolucao` (
  `id` int PRIMARY KEY,
  `if_devolucao` int NOT NULL,
  `id_filme_emprestimo` int NOT NULL
);

ALTER TABLE `filmes` ADD FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id`);

ALTER TABLE `dvds` ADD FOREIGN KEY (`id_filme`) REFERENCES `filmes` (`id`);

ALTER TABLE `atores_filme` ADD FOREIGN KEY (`id_atores`) REFERENCES `atores` (`id`);

ALTER TABLE `atores_filme` ADD FOREIGN KEY (`id_filme`) REFERENCES `filmes` (`id`);

ALTER TABLE `emprestimos` ADD FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`);

ALTER TABLE `filmes_emprestimo` ADD FOREIGN KEY (`id_dvd`) REFERENCES `filmes` (`id`);

ALTER TABLE `filmes_emprestimo` ADD FOREIGN KEY (`id_emprestimos`) REFERENCES `emprestimos` (`id`);

ALTER TABLE `devolucoes` ADD FOREIGN KEY (`id_emprestimos`) REFERENCES `emprestimos` (`id`);

ALTER TABLE `filmes_devolucao` ADD FOREIGN KEY (`if_devolucao`) REFERENCES `devolucoes` (`id`);

ALTER TABLE `filmes_devolucao` ADD FOREIGN KEY (`id_filme_emprestimo`) REFERENCES `filmes_emprestimo` (`id`);
