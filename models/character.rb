class Character < ActiveRecord::Base
  validates :character_name, presence: true, uniqueness: {case_sensitive: false}
  validates_presence_of :team
  validates_presence_of :start_loc
  I18n.enforce_available_locales = true
end