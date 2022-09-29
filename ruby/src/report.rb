class Report

  def initialize(suites)
    @suites = suites
    @total = suites.map { |suite| suite.total }.sum
    @passed = suites.map { |suite| suite.passed }
    @failed = suites.map { |suite| suite.failed }
    @broken = suites.map { |suite| suite.broken }
  end

  def print
    summary
  end

  def summary
    puts "Total #{@total}, Passed: #{@passed.flatten.size}, Failed: #{@failed.flatten.size}, Broken #{@broken.flatten.size}"
  end
end
