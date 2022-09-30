require 'colorize'

class Report

  def initialize(suites)
    @suites = suites
    @total = suites.map(&:total).sum
    @passed = suites.map(&:passed).flatten
    @failed = suites.map(&:failed).flatten
    @broken = suites.map(&:broken).flatten
  end

  def print
    summary
  end

  def summary
    puts "\nTotal #{@total}, Passed: #{@passed.size}, Failed: #{@failed.size}, Broken #{@broken.size}\n"

    unless @passed.empty?
      puts "\nTests exitosos:".green
      @passed.each do |t|
        puts "\tname: #{t.test_name}"
      end
    end
    unless @failed.empty?
      puts "\nTests fallidos:".red
      @failed.each do |t|
        puts "\tname: #{t.test_name}, reason: #{t.description}"
      end
    end
    unless @broken.empty?
      puts "\nTests explotados:".yellow
      @broken.each do |t|
        puts "\tname: #{t.test_name}, reason: #{t.description}"
      end
    end
  end
end
