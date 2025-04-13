import csv
from collections import defaultdict

print("Starting script...")

# Initialize dictionaries to store aggregated data
amounts = defaultdict(float)
cost_basis = defaultdict(float)
proceeds = defaultdict(float)
gains = defaultdict(float)
sources = defaultdict(set)  # Use set to collect unique sources
latest_values = defaultdict(lambda: {
    'Transaction Type': '',
    'Transaction ID': '',
    'Tax lot ID': '',
    'Date Acquired': '',
    'Holding period': ''
})

print("Opening input file...")
# Process all data in a single pass
with open('Coinbase-2024-CB-TURBOTAX-GAINLOSS_.csv', 'r') as f:
    reader = csv.reader(f)
    header = next(reader)  # Skip header row
    print(f"Header row: {header}")
    
    for i, row in enumerate(reader):
        if i == 0:  # Print first row for debugging
            print(f"First row: {row}")
        
        if len(row) >= 12:  # Ensure row has enough columns
            asset = row[3]  # Asset Name is in column 4 (0-based index 3)
            try:
                # Aggregate numeric values
                amounts[asset] += float(row[4])  # Amount
                if row[6]:  # Cost basis
                    cost_basis[asset] += float(row[6])
                if row[8]:  # Proceeds
                    proceeds[asset] += float(row[8])
                if row[9]:  # Gains (Losses)
                    gains[asset] += float(row[9])
                
                # Collect unique sources
                if row[11]:  # Data source
                    sources[asset].add(row[11])
                
                # Update latest values if we have newer data or if it's empty
                current_date = latest_values[asset]['Date Acquired']
                if not current_date or (row[5] and row[5] >= current_date):
                    latest_values[asset].update({
                        'Transaction Type': row[0],
                        'Transaction ID': row[1],
                        'Tax lot ID': row[2],
                        'Date Acquired': row[5],
                        'Holding period': row[10] if len(row) > 10 else ''
                    })
            except (ValueError, IndexError) as e:
                print(f"Error processing row {i}: {e}")
                continue

print(f"Processed data summary:")
print(f"Number of assets: {len(amounts)}")
print(f"Assets found: {list(amounts.keys())}")

print("Writing output file...")
# Write the aggregated data to a new CSV
with open('aggregated_trades_output.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerow([
        'Transaction Type', 'Transaction ID', 'Tax lot ID', 'Asset name', 'Amount',
        'Date Acquired', 'Cost basis (USD)', 'Date of Disposition', 'Proceeds (USD)',
        'Gains (Losses) (USD)', 'Holding period (Days)', 'Data source'
    ])
    
    for asset in sorted(amounts.keys()):
        # Format sources as a semicolon-separated list, or 'Unknown' if no sources
        source_list = sorted(sources[asset])
        sources_str = '; '.join(source_list) if source_list else 'Unknown'
        
        writer.writerow([
            latest_values[asset]['Transaction Type'],
            latest_values[asset]['Transaction ID'],
            latest_values[asset]['Tax lot ID'],
            asset,
            amounts[asset],
            latest_values[asset]['Date Acquired'],
            cost_basis[asset],
            '',  # Date of Disposition (not tracked)
            proceeds[asset],
            gains[asset],
            latest_values[asset]['Holding period'],
            sources_str
        ])

print("Script completed successfully!") 