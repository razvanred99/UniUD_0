#include <stdio.h>
#include <stdlib.h>

void readline(char s[], unsigned int size);
void parseArray(int **v, unsigned int *size, char s[]);
int partition(int array[], int lower, int upper);
void swap(int array[], int first, int second);
int quick_select(int array[], int lower, int upper, int k);

int main(int argc, char** argv) {
	int *v;
    unsigned int size; 
    char str[10000];
    unsigned int k;
    //leggo l'array, la posizione, ed eseguo la ricerca
	readline(str, 10000);
    parseArray(&v, &size, str);
    scanf("%d", &k);
    printf("%d\n", quick_select(v, 0, size - 1, k - 1));
}

//leggo la riga come stringa
void readline(char s[], unsigned int size) {
  unsigned int i= 0;
  char c;
  size--;
  for(c= getchar(); c != '\n';) {
    if(i < size) {
      s[i]= c;
      i++;
    }
    c= getchar();
  }
  if(i >= 0){
    s[i]= 0;
    i--;
  }
  
  while(s[i] == ' ') {
    s[i] = 0;
    i--;
  }
}

//produco un vettore dalla stringa
void parseArray(int **v, unsigned int *size, char s[]) {
  *size= 0;
  for(unsigned int i= 0; s[i] != 0; i++) {
    if(s[i] == ' ')
      (*size)++;
  }
  (*size)++;
  *v= malloc(*size * sizeof(int));
  for(unsigned int i= 0; i < *size; i++) {
    sscanf(s, "%d", &((*v)[i]));
    for(; *s != 0 && *s != ' '; s+= sizeof(char));
    s+= sizeof(char);
  }
}

int partition(int array[], int lower, int upper) {	
	//swap(array, k, upper); //swap non necessario, scelgo sempre l'ultimo come pivot
	int break_even = lower - 1;
	
	for(int i = lower; i <= upper; i++){
		//eseguo lo scambio se l'elemento � pi� grande dell'ultimo
		if(array[i] <= array[upper]){
			break_even++;
			swap(array, break_even, i);
		}
	}	
	
	//restituisco il pivot
	return break_even;
}

//scambio due elementi
void swap(int array[], int first, int second) {
	int temp = array[first];
	array[first] = array[second];
	array[second] = temp;
}

int quick_select(int array[], int lower, int upper, int k) {
	//termina se l'array � vuoto
	if(lower > k || upper < k) {
		return -1;
	}
	//partioziono l'array senza criterio
	int break_even = partition(array, lower, upper);
	
	if(k == break_even) {
		//se il pivot � la posizione che cerco restituisco l'elemento
		return array[k];
	} else if(k > break_even) {
		//altrimenti ricorro sulla partizione che contiene la posizione
		return quick_select(array, break_even + 1, upper, k);
	} else {
		return quick_select(array, lower, break_even - 1, k);
	}
	//n.b.: al termine l'elemento selezionato si trova nella posizione k
}

