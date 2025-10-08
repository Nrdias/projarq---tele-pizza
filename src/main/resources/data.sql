-- Inserção dos clientes
INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES ('9001', 'Huguinho Pato', '51985744566', 'Rua das Flores, 100', 'huguinho.pato@email.com', '123456');
INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES ('9002', 'Luizinho Pato', '5199172079', 'Av. Central, 200', 'zezinho.pato@email.com', '123456');
INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES ('222', 'João Silva', '51999887766', 'Rua dos Testes, 123', 'joao.silva@email.com', '123456');

-- Inserção dos ingredientes
INSERT INTO ingredientes (id, descricao) VALUES (1, 'Disco de pizza');
INSERT INTO ingredientes (id, descricao) VALUES (2, 'Porcao de tomate');
INSERT INTO ingredientes (id, descricao) VALUES (3, 'Porcao de mussarela');
INSERT INTO ingredientes (id, descricao) VALUES (4, 'Porcao de presunto');
INSERT INTO ingredientes (id, descricao) VALUES (5, 'Porcao de calabresa');
INSERT INTO ingredientes (id, descricao) VALUES (6, 'Molho de tomate (200ml)');
INSERT INTO ingredientes (id, descricao) VALUES (7, 'Porcao de azeitona');
INSERT INTO ingredientes (id, descricao) VALUES (8, 'Porcao de oregano');
INSERT INTO ingredientes (id, descricao) VALUES (9, 'Porcao de cebola');

-- Inserção dos itens de estoque
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (1, 30, 1);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (2, 30, 2);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (3, 30, 3);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (4, 30, 4);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (5, 30, 5);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (6, 30, 6);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (7, 30, 7);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (8, 30, 8);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (9, 30, 9);

-- Inserção das receitas 
INSERT INTO receitas (id, titulo) VALUES (1, 'Pizza calabresa');
INSERT INTO receitas (id, titulo) VALUES (2, 'Pizza queijo e presunto');
INSERT INTO receitas (id, titulo) VALUES (3, 'Pizza margherita');

-- Associação dos ingredientes à receita Pizza calabresa
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 5); -- Porcao de calabresa
-- Associação dos ingredientes à receita Pizza queijo e presunto
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 4); -- Porcao de presunto
-- Associação dos ingredientes à receita Pizza margherita
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 1); -- Disco de pizza
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 6); -- Molho de tomate (200ml)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 3); -- Porcao de mussarela
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 8); -- Porcao de cebola

-- insercao dos produtos
INSERT INTO produtos (id,descricao,preco) VALUES (1,'Pizza calabresa',5500);
INSERT INTO produtos (id,descricao,preco) VALUES (2,'Pizza queijo e presunto',6000);
INSERT INTO produtos (id,descricao,preco) VALUES (3,'Pizza margherita',4000);

-- Associação dos produtos com as receitas
INSERT INTO produto_receita (produto_id,receita_id) VALUES(1,1);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(2,2);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(3,3);

-- Insercao dos cardapios
INSERT INTO cardapios (id,titulo) VALUES(1,'Cardapio de Agosto');
INSERT INTO cardapios (id,titulo) VALUES(2,'Cardapio de Setembro');

-- Associação dos cardapios com os produtos
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,2);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,3);

INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,3);

-- Inserção dos pedidos
INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) 
VALUES (1, '9001', '2024-01-15 19:30:00', 'ENTREGUE', 55.00, 8.25, 5.00, 58.25);

INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) 
VALUES (2, '9002', '2024-01-16 20:15:00', 'PREPARACAO', 60.00, 9.00, 0.00, 69.00);

INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) 
VALUES (3, '9001', '2024-01-17 18:45:00', 'NOVO', 40.00, 6.00, 0.00, 46.00);

INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) 
VALUES (4, '9002', '2024-01-18 21:00:00', 'PAGO', 115.00, 17.25, 10.00, 122.25);

INSERT INTO pedidos (id, cliente_cpf, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) 
VALUES (5, '9001', NULL, 'APROVADO', 55.00, 8.25, 0.00, 63.25);

-- Inserção dos itens dos pedidos
-- Pedido 1: 1x Pizza calabresa
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (1, 1, 1, 1);

-- Pedido 2: 1x Pizza queijo e presunto
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (2, 2, 2, 1);

-- Pedido 3: 1x Pizza margherita
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (3, 3, 3, 1);

-- Pedido 4: 2x Pizza calabresa + 1x Pizza queijo e presunto
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (4, 4, 1, 2);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (5, 4, 2, 1);

-- Pedido 5: 1x Pizza calabresa (APROVADO, não pago - pode ser cancelado)
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade) VALUES (6, 5, 1, 1);