require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Character < ActiveRecord::Base
  validates :character_name, presence: true, uniqueness: {case_sensitive: false}
  validates_presence_of :team
  I18n.enforce_available_locales = true
end

describe Character do

  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {character_name: 'bob',
            image: 'bobthedumpster.png',
            team: 'victims'}
    Character.create(@attr)
    p Character.all
  end

  it "should not allow you to enter duplicate characters" do
    expect(Character.create(@attr)).to_not be_valid
  end

  it { should validate_presence_of :team }
  it { should validate_uniqueness_of :character_name }
  it { should validate_presence_of :character_name }
end