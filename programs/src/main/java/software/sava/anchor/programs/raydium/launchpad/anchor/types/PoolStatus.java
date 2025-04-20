package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

// Represents the different states a pool can be in
// * Fund - Initial state where pool is accepting funds
// * Migrate - Pool funding has ended and waiting for migration
// * Trade - Pool migration is complete and amm trading is enabled
public enum PoolStatus implements Borsh.Enum {

  Fund,
  Migrate,
  Trade;

  public static PoolStatus read(final byte[] _data, final int offset) {
    return Borsh.read(PoolStatus.values(), _data, offset);
  }
}