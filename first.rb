sentences_with_audio = "files/sentences_with_audio.txt"
sentences = "files/tmp/sentences.txt"
my_sentences_with_audio = "files/SentencesWithAudio.txt"
a=[]
File.foreach(sentences_with_audio) do |line|
  a.push(line.chomp)
end
b=[]
File.foreach(sentences) do |line|

  x=''
  words = line.split(/\t/)


    if a.include? words[0]
      x = true
    else
      x = false
    end

    b.push(line.chomp + "\t" + x.to_s)
end

File.open(my_sentences_with_audio, "w+") do |f|
  b.each { |element| f.puts(element) }
end

