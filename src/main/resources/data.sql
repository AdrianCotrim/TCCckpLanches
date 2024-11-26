INSERT INTO usuarios (email_usuario, nome_usuario, senha_usuario, nivel_acesso, status, status_conta)
SELECT 'admin@gmail.com', 'admin', '$2a$10$EDUA5/WVkZrnnKEDFh2DkOt16ns0Bx91MjUBQu4zdztIbCaxCGnJq', 'ADMIN', 'ATIVO', 'ATIVO'
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email_usuario = 'admin@gmail.com');

INSERT INTO usuarios (email_usuario, nome_usuario, senha_usuario, nivel_acesso, status, status_conta)
SELECT 'user@gmail.com', 'user', '$2a$10$u3uQTaviuD.30ay6bAKc5eGr8a2IXorQN943K0cF.vN25hPjdchrW', 'USER', 'ATIVO', 'ATIVO'
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email_usuario = 'user@gmail.com');
