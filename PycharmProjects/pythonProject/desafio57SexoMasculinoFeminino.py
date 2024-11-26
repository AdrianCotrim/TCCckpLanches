sexo = str(input('Informe seu sexo: [M/F]')).upper()
while sexo not in 'MF':
    sexo = str(input('Dados inválidos. Por favor insira novamente: [M/F]')).upper()
if sexo == 'F':
    print('Seu sexo é Feminino')
else:
    print('Seu sexo é Masculino')

