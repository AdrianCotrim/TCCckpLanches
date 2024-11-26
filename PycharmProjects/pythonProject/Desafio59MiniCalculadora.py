valor1 = int(input('Insira um valor: '))
valor2 = int(input('Insira um valor: '))
calculo = 0
while calculo == 4:
    calculo = int(input('[1]Somar [2] multiplicar 3[maior] 4[novos números] 5[sair do programa]'))
    valor1 = int(input('Insira um valor: '))
    valor2 = int(input('Insira um valor: '))
while calculo != 5:
    calculo = int(input('[1]Somar [2] multiplicar 3[maior] 4[novos números] 5[sair do programa]'))
    if calculo == 1:
        print(valor1+valor2)
    if calculo == 2:
        print(valor1*valor2)
    if calculo == 3:
        if valor1 > valor2:
            print("{} é maior que {}".format(valor1, valor2))
        else:
            print("{} é maior que {}".format(valor2, valor1))
print('Encerrado')
