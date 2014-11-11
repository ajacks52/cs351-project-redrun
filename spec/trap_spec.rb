require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Trap < ActiveRecord::Base
  belongs_to :kiosk, :class_name => 'Kiosk', :foreign_key => 'kiosk_id'
  validates_presence_of :kiosk_id
  has_one :button, :class_name => 'Button'
  I18n.enforce_available_locales = true
end

describe Trap do
  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {trap_type: 'spikey',
        kiosk_id: 1}
    Trap.create(@attr)
    p Trap.all
  end

  it { should belong_to :kiosk }
  it { should validate_presence_of :kiosk_id }
  it { should have_one :button }
end