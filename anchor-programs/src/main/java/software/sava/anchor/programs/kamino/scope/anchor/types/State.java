package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record State(PublicKey msolMint,
                    PublicKey adminAuthority,
                    PublicKey operationalSolAccount,
                    PublicKey treasuryMsolAccount,
                    int reserveBumpSeed,
                    int msolMintAuthorityBumpSeed,
                    long rentExemptForTokenAcc,
                    Fee rewardFee,
                    StakeSystem stakeSystem,
                    ValidatorSystem validatorSystem,
                    LiqPool liqPool,
                    long availableReserveBalance,
                    long msolSupply,
                    long msolPrice,
                    // count tickets for delayed-unstake
                    long circulatingTicketCount,
                    // total lamports amount of generated and not claimed yet tickets
                    long circulatingTicketBalance,
                    long lentFromReserve,
                    long minDeposit,
                    long minWithdraw,
                    long stakingSolCap,
                    long emergencyCoolingDown) implements Borsh {

  public static final int BYTES = 568;

  public static State read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var msolMint = readPubKey(_data, i);
    i += 32;
    final var adminAuthority = readPubKey(_data, i);
    i += 32;
    final var operationalSolAccount = readPubKey(_data, i);
    i += 32;
    final var treasuryMsolAccount = readPubKey(_data, i);
    i += 32;
    final var reserveBumpSeed = _data[i] & 0xFF;
    ++i;
    final var msolMintAuthorityBumpSeed = _data[i] & 0xFF;
    ++i;
    final var rentExemptForTokenAcc = getInt64LE(_data, i);
    i += 8;
    final var rewardFee = Fee.read(_data, i);
    i += Borsh.len(rewardFee);
    final var stakeSystem = StakeSystem.read(_data, i);
    i += Borsh.len(stakeSystem);
    final var validatorSystem = ValidatorSystem.read(_data, i);
    i += Borsh.len(validatorSystem);
    final var liqPool = LiqPool.read(_data, i);
    i += Borsh.len(liqPool);
    final var availableReserveBalance = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    i += 8;
    final var msolPrice = getInt64LE(_data, i);
    i += 8;
    final var circulatingTicketCount = getInt64LE(_data, i);
    i += 8;
    final var circulatingTicketBalance = getInt64LE(_data, i);
    i += 8;
    final var lentFromReserve = getInt64LE(_data, i);
    i += 8;
    final var minDeposit = getInt64LE(_data, i);
    i += 8;
    final var minWithdraw = getInt64LE(_data, i);
    i += 8;
    final var stakingSolCap = getInt64LE(_data, i);
    i += 8;
    final var emergencyCoolingDown = getInt64LE(_data, i);
    return new State(msolMint,
                     adminAuthority,
                     operationalSolAccount,
                     treasuryMsolAccount,
                     reserveBumpSeed,
                     msolMintAuthorityBumpSeed,
                     rentExemptForTokenAcc,
                     rewardFee,
                     stakeSystem,
                     validatorSystem,
                     liqPool,
                     availableReserveBalance,
                     msolSupply,
                     msolPrice,
                     circulatingTicketCount,
                     circulatingTicketBalance,
                     lentFromReserve,
                     minDeposit,
                     minWithdraw,
                     stakingSolCap,
                     emergencyCoolingDown);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    msolMint.write(_data, i);
    i += 32;
    adminAuthority.write(_data, i);
    i += 32;
    operationalSolAccount.write(_data, i);
    i += 32;
    treasuryMsolAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) reserveBumpSeed;
    ++i;
    _data[i] = (byte) msolMintAuthorityBumpSeed;
    ++i;
    putInt64LE(_data, i, rentExemptForTokenAcc);
    i += 8;
    i += Borsh.write(rewardFee, _data, i);
    i += Borsh.write(stakeSystem, _data, i);
    i += Borsh.write(validatorSystem, _data, i);
    i += Borsh.write(liqPool, _data, i);
    putInt64LE(_data, i, availableReserveBalance);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    putInt64LE(_data, i, msolPrice);
    i += 8;
    putInt64LE(_data, i, circulatingTicketCount);
    i += 8;
    putInt64LE(_data, i, circulatingTicketBalance);
    i += 8;
    putInt64LE(_data, i, lentFromReserve);
    i += 8;
    putInt64LE(_data, i, minDeposit);
    i += 8;
    putInt64LE(_data, i, minWithdraw);
    i += 8;
    putInt64LE(_data, i, stakingSolCap);
    i += 8;
    putInt64LE(_data, i, emergencyCoolingDown);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
