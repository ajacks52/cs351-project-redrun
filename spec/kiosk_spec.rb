require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Kiosk < ActiveRecord::Base
  validates_presence_of :location
  validates_presence_of :cooldown
  has_one :trap, :class_name => "Trap"
  has_one :button, :class_name => "Button"
  I18n.enforce_available_locales = true
end

describe Kiosk do

  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {location: '12,50,77',
        cooldown: '280.39', map_id: 1}
    Kiosk.create(@attr)
    p Kiosk.all
  end

  it { should validate_presence_of :location }
  it { should validate_presence_of :cooldown }
  it { should have_one :trap }
  it { should have_one :button }
end