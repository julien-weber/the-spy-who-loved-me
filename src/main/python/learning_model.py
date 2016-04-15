import pandas as pd
import statsmodels.api as sm
import numpy as np
import sys
filename = sys.argv[1] 
data = np.loadtxt(filename, delimiter=',')
[nRow,nCol] = data.shape
data_train = data[:,0:nCol-2]
labels_train = data[:,nCol-1]
logit = sm.Logit(labels_train, data_train)
result = logit.fit()
sys.stdout = open("/Users/b.behmardi/weight.txt", "w")
print result.params
