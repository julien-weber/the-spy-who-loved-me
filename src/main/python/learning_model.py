import pandas as pd
import statsmodels.api as sm
import numpy as np

  data = np.loadtxt(filename, delimiter=',')
  [nRow,nCol] = data.shape
  data_train = data[:,0:nCol-2]
  labels_train = data[:,nCol-1]
  logit = sm.Logit(labels_train, data_train)
  result = logit.fit()
  print result.summary()
  return result.params
