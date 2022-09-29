class Suite
  attr_accessor :cls, :tests

  def initialize(name)
    @cls = name
    @tests = []
  end

  def add_tests(tests)
    tests.each do |test|
      add_test(test)
    end
  end

  def add_test(test)
    @tests.push(Test.new(test)) if @cls.instance_methods.include? test 
  end

  def run_tests
    cls.include SyntaxSugar
    context = cls.new
    @tests.each do |test|
      test.execute context
    end
  end

  def total
    @tests.length
  end

  def passed
    @tests.filter { |test| test.status == true }
  end

  def failed
    @tests.filter { |test| test.status == false }
  end

  def broken
    @tests.filter { |test| test.status == "broken" }
  end
end
