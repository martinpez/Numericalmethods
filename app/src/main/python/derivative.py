import sympy as sp

def derive_function(expression):
    try:
        x = sp.Symbol('x')
        # Reemplaza ^ por ** para que sympy pueda interpretar la potencia correctamente
        expression = expression.replace('^', '**')
        func = sp.sympify(expression)  # Convierte la expresión en una forma que sympy pueda manejar
        derivative = sp.diff(func, x)  # Calcula la derivada
        #derivative = derivative.replace('^', '**')
        return str(derivative)  # Retorna la derivada como una cadena
    except Exception as e:
        return f"Error: {str(e)}"