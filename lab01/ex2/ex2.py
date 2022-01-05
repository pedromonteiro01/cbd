letters = {} # {letter : total_number}

# open names.txt to read
with open('names.txt', 'r') as f:
    for word in f:
        letter = word[0] #first letter of the word
        
        if letter in letters:
            letters[letter] += 1
        else:
            letters[letter] = 1

#SET A <total de nomes que começa pela letra ‘A’/’a’>
#SET B <total de nomes que começa pela letra ‘B’/’b’> 

#create names_counting.txt                   
with open('names_counting.txt', 'w') as f:
    for key, value in letters.items():
        f.write(f'SET {key.upper()} {value}\n')