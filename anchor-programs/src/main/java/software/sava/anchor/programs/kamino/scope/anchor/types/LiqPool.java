package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiqPool(PublicKey lpMint,
                      int lpMintAuthorityBumpSeed,
                      int solLegBumpSeed,
                      int msolLegAuthorityBumpSeed,
                      PublicKey msolLeg,
                      // Liquidity target. If the Liquidity reach this amount, the fee reaches lp_min_discount_fee
                      long lpLiquidityTarget,
                      // Liquidity pool max fee
                      Fee lpMaxFee,
                      // SOL/mSOL Liquidity pool min fee
                      Fee lpMinFee,
                      // Treasury cut
                      Fee treasuryCut,
                      long lpSupply,
                      long lentFromSolLeg,
                      long liquiditySolCap) implements Borsh {

  public static final int BYTES = 111;

  public static LiqPool read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var lpMintAuthorityBumpSeed = _data[i] & 0xFF;
    ++i;
    final var solLegBumpSeed = _data[i] & 0xFF;
    ++i;
    final var msolLegAuthorityBumpSeed = _data[i] & 0xFF;
    ++i;
    final var msolLeg = readPubKey(_data, i);
    i += 32;
    final var lpLiquidityTarget = getInt64LE(_data, i);
    i += 8;
    final var lpMaxFee = Fee.read(_data, i);
    i += Borsh.len(lpMaxFee);
    final var lpMinFee = Fee.read(_data, i);
    i += Borsh.len(lpMinFee);
    final var treasuryCut = Fee.read(_data, i);
    i += Borsh.len(treasuryCut);
    final var lpSupply = getInt64LE(_data, i);
    i += 8;
    final var lentFromSolLeg = getInt64LE(_data, i);
    i += 8;
    final var liquiditySolCap = getInt64LE(_data, i);
    return new LiqPool(lpMint,
                       lpMintAuthorityBumpSeed,
                       solLegBumpSeed,
                       msolLegAuthorityBumpSeed,
                       msolLeg,
                       lpLiquidityTarget,
                       lpMaxFee,
                       lpMinFee,
                       treasuryCut,
                       lpSupply,
                       lentFromSolLeg,
                       liquiditySolCap);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lpMint.write(_data, i);
    i += 32;
    _data[i] = (byte) lpMintAuthorityBumpSeed;
    ++i;
    _data[i] = (byte) solLegBumpSeed;
    ++i;
    _data[i] = (byte) msolLegAuthorityBumpSeed;
    ++i;
    msolLeg.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lpLiquidityTarget);
    i += 8;
    i += Borsh.write(lpMaxFee, _data, i);
    i += Borsh.write(lpMinFee, _data, i);
    i += Borsh.write(treasuryCut, _data, i);
    putInt64LE(_data, i, lpSupply);
    i += 8;
    putInt64LE(_data, i, lentFromSolLeg);
    i += 8;
    putInt64LE(_data, i, liquiditySolCap);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
