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
    puts "Total #{@total}, Passed: #{@passed.size}, Failed: #{@failed.size}, Broken #{@broken.size}"

    #unless @passed.empty?
    # puts "Tests exitosos:".green
    # @passed.each do |t|
    #   puts "\t #{t.test_name}"
    # end
    #end
    #unless @failed.empty?
    # puts "Tests fallidos:".red
    # @failed.each do |t|
    #   puts "\t #{t.test_name}, reason: #{t.reason}"
    # end
    #end
  end
end
