require 'active_record'

# Migration to create the Buttons tables
class CreateMaps < ActiveRecord::Migration

  def change
    create_table :maps do |t|
    end
  end
end