package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;

public record LendingAccountLiquidateEvent(AccountEventHeader header,
                                           PublicKey liquidateeMarginfiAccount,
                                           PublicKey liquidateeMarginfiAccountAuthority,
                                           PublicKey assetBank,
                                           PublicKey assetMint,
                                           PublicKey liabilityBank,
                                           PublicKey liabilityMint,
                                           double liquidateePreHealth,
                                           double liquidateePostHealth,
                                           LiquidationBalances preBalances,
                                           LiquidationBalances postBalances) implements Borsh {

  public static LendingAccountLiquidateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = AccountEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var liquidateeMarginfiAccount = readPubKey(_data, i);
    i += 32;
    final var liquidateeMarginfiAccountAuthority = readPubKey(_data, i);
    i += 32;
    final var assetBank = readPubKey(_data, i);
    i += 32;
    final var assetMint = readPubKey(_data, i);
    i += 32;
    final var liabilityBank = readPubKey(_data, i);
    i += 32;
    final var liabilityMint = readPubKey(_data, i);
    i += 32;
    final var liquidateePreHealth = getFloat64LE(_data, i);
    i += 8;
    final var liquidateePostHealth = getFloat64LE(_data, i);
    i += 8;
    final var preBalances = LiquidationBalances.read(_data, i);
    i += Borsh.len(preBalances);
    final var postBalances = LiquidationBalances.read(_data, i);
    return new LendingAccountLiquidateEvent(header,
                                            liquidateeMarginfiAccount,
                                            liquidateeMarginfiAccountAuthority,
                                            assetBank,
                                            assetMint,
                                            liabilityBank,
                                            liabilityMint,
                                            liquidateePreHealth,
                                            liquidateePostHealth,
                                            preBalances,
                                            postBalances);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    liquidateeMarginfiAccount.write(_data, i);
    i += 32;
    liquidateeMarginfiAccountAuthority.write(_data, i);
    i += 32;
    assetBank.write(_data, i);
    i += 32;
    assetMint.write(_data, i);
    i += 32;
    liabilityBank.write(_data, i);
    i += 32;
    liabilityMint.write(_data, i);
    i += 32;
    putFloat64LE(_data, i, liquidateePreHealth);
    i += 8;
    putFloat64LE(_data, i, liquidateePostHealth);
    i += 8;
    i += Borsh.write(preBalances, _data, i);
    i += Borsh.write(postBalances, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header)
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 8
         + 8
         + Borsh.len(preBalances)
         + Borsh.len(postBalances);
  }
}
