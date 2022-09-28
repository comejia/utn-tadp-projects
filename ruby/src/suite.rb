class Suite
  attr_accessor :name, :tests

  def initialize(name)
    @name = name
    @tests = []
  end

  def add_tests(tests)
    tests.each do |test|
      @tests.push(Test.new(test))
    end
  end
end
