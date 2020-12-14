import numpy as np
import matplotlib.pyplot as plt

def mexican_hat(phi):
	result = 0.25 * (phi ** 2 - 1) **2
	return result
	
def mexican_hat_prime(phi):
	result = (phi **2 - 1) * phi
	return result
	
N = 1000
scale = 2.
x = np.linspace(0, scale, N)
phi = np.zeros((N,))
phi[0] = 0.
phi_prime = np.zeros((N,))
phi_prime[0] = 1. / (2. ** 0.5)

dx = scale / N

for i in range(N - 1):
	phi[i + 1] = phi[i] + phi_prime[i] * dx
	phi_prime[i + 1] = phi_prime[i] + mexican_hat_prime(phi[i]) * dx
	
#plt.plot(x, phi, 'r')
#plt.plot(x, np.tanh(1. / (2. ** 0.5) * x), 'b')

plt.figure(figsize=(12, 7))
plt.subplot(1, 2, 1)
plt.plot(x, phi, 'b')
plt.subplot(1, 2, 2)
plt.plot(x, np.tanh(1. / (2. ** 0.5) * x), 'r')
plt.show()
	
	