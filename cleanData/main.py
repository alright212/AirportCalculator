import pandas as pd

# Load the data with specified column names
file_path = 'airport_data.csv'  # Adjust the path if necessary
column_names = ['iata_code', 'name', 'city', 'country', 'latitude', 'longitude']
data = pd.read_csv(file_path, names=column_names, header=None)

# Remove the first row if it contains headers
if data.iloc[0].str.contains('iata_code', case=False).any():
    data = data.iloc[1:]

# Remove rows with 'N/A' or NaN in the IATA code
cleaned_data = data[data['iata_code'].notna() & (data['iata_code'] != 'N/A')]

# Save the cleaned data to a new CSV file
cleaned_data.to_csv('airport_data_updated.csv', index=False)

# Print the first few rows of the cleaned data to verify
print(cleaned_data.head())
