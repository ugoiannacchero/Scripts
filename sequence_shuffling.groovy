// Get sequences from the command line, split by commas
def sequences = params.sequences?.split(",") ?: []

// Check if at least one sequence was provided
if (sequences.size() == 0) {
    println "Error: Please provide one or more sequences with '--sequences'."
    System.exit(1)
}

// Prepare the output file
def outputFile = new File("shuffled_sequences.txt")
outputFile.text = ""  // Clear previous content

// Function to shuffle dinucleotides
def shuffleDinucleotides = { String seq ->
    // Split sequence into dinucleotides
    def dinucs = []
    for (int i = 0; i < seq.length() - 1; i += 2) {
        dinucs << seq[i..i+1]
    }
    // Shuffle dinucleotides array
    Collections.shuffle(dinucs)
    // Recombine shuffled dinucleotides
    dinucs.join()
}

// Generate 5 shuffles for each sequence, ensuring GC content and length are preserved
sequences.each { reference ->
    outputFile << "Original sequence: $reference\n"
    5.times { i ->
        def shuffled = shuffleDinucleotides(reference)
        outputFile << "Shuffled sequence ${i + 1}: $shuffled\n"
    }
    outputFile << "\n"
}

println "Shuffled sequences have been saved to shuffled_sequences.txt"
