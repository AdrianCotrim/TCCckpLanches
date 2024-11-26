#Sequência de Fibonacci 0 1 1 2 3 5 8 13 21 34 55 89
#A Sequência de Fibonacci começa com 0 e 1, básicamente somamos esses dois números e vamos somando o resultado junto com o número anterior
#exemplo 0 + 1 = 1 próximo termo da sequência 1 + 1 = 2 próximo termo da sequência 2 + 1 = 3 próximo termo 3 + 2 = 5

print('-'*30)
print('Sequência de Fibonacci')
print('-'*30)
numerousuario = int(input('Insira a Posição que deseja: '))
contador = 2
numero1 = 0
numero2 = 1
print('{} > {} '.format(numero1, numero2), end='')
while contador != numerousuario:
    numero3 = numero1 + numero2
    print('> {} '.format(numero3), end='')
    numero1 = numero2
    numero2 = numero3
    contador += 1
print('> FIM')