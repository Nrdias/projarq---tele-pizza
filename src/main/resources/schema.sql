create table if not exists clientes(
  cpf varchar(15) not null primary key,
  nome varchar(100) not null,
  celular varchar(20) not null,
  endereco varchar(255) not null,
  email varchar(255) not null unique,
  senha varchar(255) not null
);

create table if not exists ingredientes (
  id bigint primary key,
  descricao varchar(255) not null
);

create table if not exists itensEstoque(
    id bigint primary key,
    quantidade int,
    ingrediente_id bigint,
    foreign key (ingrediente_id) references ingredientes(id)
);

-- Tabela Receita
create table if not exists receitas (
  id bigint primary key,
  titulo varchar(255) not null
);

-- Tabela de relacionamento entre Receita e Ingrediente
create table if not exists receita_ingrediente (
  receita_id bigint not null,
  ingrediente_id bigint not null,
  primary key (receita_id, ingrediente_id),
  foreign key (receita_id) references receitas(id),
  foreign key (ingrediente_id) references ingredientes(id)
);

-- Tabela de Produtos
create table if not exists produtos (
  id bigint primary key,
  descricao varchar(255) not null,
  preco bigint
);

-- Tabela de relacionamento entre Produto e Receita
create table if not exists produto_receita (
  produto_id bigint not null,
  receita_id bigint not null,
  primary key (produto_id,receita_id),
  foreign key (produto_id) references produtos(id),
  foreign key (receita_id) references receitas(id)
);

-- Tabela de Cardapios
create table if not exists cardapios (
  id bigint primary key,
  titulo varchar(255) not null
);

-- Tabela de relacionamento entre Cardapio e Produto
create table if not exists cardapio_produto (
  cardapio_id bigint not null,
  produto_id bigint not null,
  primary key (cardapio_id,produto_id),
  foreign key (cardapio_id) references cardapios(id),
  foreign key (produto_id) references produtos(id)
);

-- Tabela de Pedidos
create table if not exists pedidos (
  id bigint primary key,
  cliente_cpf varchar(15) not null,
  data_hora_pagamento timestamp,
  status varchar(20) not null,
  valor double not null,
  impostos double not null,
  desconto double not null,
  valor_cobrado double not null,
  foreign key (cliente_cpf) references clientes(cpf)
);

-- Tabela de Itens do Pedido
create table if not exists itens_pedido (
  id bigint primary key,
  pedido_id bigint not null,
  produto_id bigint not null,
  quantidade int not null,
  foreign key (pedido_id) references pedidos(id),
  foreign key (produto_id) references produtos(id)
);

-- Tabela de Histórico de Status do Pedido (para auditoria e rastreamento)
create table if not exists historico_status_pedido (
  id bigint primary key,
  pedido_id bigint not null,
  status_anterior varchar(20),
  status_novo varchar(20) not null,
  data_mudanca timestamp not null,
  observacoes varchar(500),
  foreign key (pedido_id) references pedidos(id)
);