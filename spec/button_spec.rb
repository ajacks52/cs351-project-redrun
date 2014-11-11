require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Button < ActiveRecord::Base
  belongs_to :kiosk, :class_name => 'Kiosk', :foreign_key => 'kiosk_id'
  belongs_to :trap, :class_name => 'Trap', :foreign_key => 'trap_id'
  validates_presence_of :kiosk_id
  validates_presence_of :trap_id
  I18n.enforce_available_locales = true
end

describe Button do
  # To run before each it statement:
  # before(:each) do

  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {state: false,
             kiosk_id: 1,
             trap_id: 1}
    Button.create(@attr)
    p Button.all
  end

  it { should belong_to :kiosk }
  it { should belong_to :trap }
  it { should validate_presence_of :kiosk_id }
  it { should validate_presence_of :trap_id }
end