require 'active_record'

# Migration to create the Kiosk tables
class CreateKiosks < ActiveRecord::Migration

  def change
    create_table :kiosks do |t|
      t.column :location, :string, null: false
      t.column :cooldown, :decimal, null: false
    end
    add_index :kiosks, [:location], :name => "index_kiosks_on_location"
    add_index :kiosks, [:cooldown], :name => "index_kiosks_on_cooldown"
  end
end