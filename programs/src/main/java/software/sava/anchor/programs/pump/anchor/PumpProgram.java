package software.sava.anchor.programs.pump.anchor;

import java.lang.String;

import java.util.List;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class PumpProgram {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedPumpProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey globalKey,
                                       final PublicKey userKey) {
    final var keys = List.of(
      createWrite(globalKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator SET_PARAMS_DISCRIMINATOR = toDiscriminator(165, 31, 134, 53, 189, 180, 130, 255);

  public static Instruction setParams(final AccountMeta invokedPumpProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey globalKey,
                                      final PublicKey userKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final PublicKey feeRecipient,
                                      final long initialVirtualTokenReserves,
                                      final long initialVirtualSolReserves,
                                      final long initialRealTokenReserves,
                                      final long tokenTotalSupply,
                                      final long feeBasisPoints) {
    final var keys = List.of(
      createWrite(globalKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[80];
    int i = writeDiscriminator(SET_PARAMS_DISCRIMINATOR, _data, 0);
    feeRecipient.write(_data, i);
    i += 32;
    putInt64LE(_data, i, initialVirtualTokenReserves);
    i += 8;
    putInt64LE(_data, i, initialVirtualSolReserves);
    i += 8;
    putInt64LE(_data, i, initialRealTokenReserves);
    i += 8;
    putInt64LE(_data, i, tokenTotalSupply);
    i += 8;
    putInt64LE(_data, i, feeBasisPoints);

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, _data);
  }

  public record SetParamsIxData(Discriminator discriminator,
                                PublicKey feeRecipient,
                                long initialVirtualTokenReserves,
                                long initialVirtualSolReserves,
                                long initialRealTokenReserves,
                                long tokenTotalSupply,
                                long feeBasisPoints) implements Borsh {  

    public static SetParamsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 80;

    public static SetParamsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feeRecipient = readPubKey(_data, i);
      i += 32;
      final var initialVirtualTokenReserves = getInt64LE(_data, i);
      i += 8;
      final var initialVirtualSolReserves = getInt64LE(_data, i);
      i += 8;
      final var initialRealTokenReserves = getInt64LE(_data, i);
      i += 8;
      final var tokenTotalSupply = getInt64LE(_data, i);
      i += 8;
      final var feeBasisPoints = getInt64LE(_data, i);
      return new SetParamsIxData(discriminator,
                                 feeRecipient,
                                 initialVirtualTokenReserves,
                                 initialVirtualSolReserves,
                                 initialRealTokenReserves,
                                 tokenTotalSupply,
                                 feeBasisPoints);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      feeRecipient.write(_data, i);
      i += 32;
      putInt64LE(_data, i, initialVirtualTokenReserves);
      i += 8;
      putInt64LE(_data, i, initialVirtualSolReserves);
      i += 8;
      putInt64LE(_data, i, initialRealTokenReserves);
      i += 8;
      putInt64LE(_data, i, tokenTotalSupply);
      i += 8;
      putInt64LE(_data, i, feeBasisPoints);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_DISCRIMINATOR = toDiscriminator(24, 30, 200, 40, 5, 28, 7, 119);

  public static Instruction create(final AccountMeta invokedPumpProgramMeta,
                                   final SolanaAccounts solanaAccounts,
                                   final PublicKey mintKey,
                                   final PublicKey mintAuthorityKey,
                                   final PublicKey bondingCurveKey,
                                   final PublicKey associatedBondingCurveKey,
                                   final PublicKey globalKey,
                                   final PublicKey mplTokenMetadataKey,
                                   final PublicKey metadataKey,
                                   final PublicKey userKey,
                                   final PublicKey eventAuthorityKey,
                                   final PublicKey programKey,
                                   final String name,
                                   final String symbol,
                                   final String uri) {
    final var keys = List.of(
      createWritableSigner(mintKey),
      createRead(mintAuthorityKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createRead(globalKey),
      createRead(mplTokenMetadataKey),
      createWrite(metadataKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _name = name.getBytes(UTF_8);
    final byte[] _symbol = symbol.getBytes(UTF_8);
    final byte[] _uri = uri.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri)];
    int i = writeDiscriminator(CREATE_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    Borsh.writeVector(_uri, _data, i);

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, _data);
  }

  public record CreateIxData(Discriminator discriminator,
                             String name, byte[] _name,
                             String symbol, byte[] _symbol,
                             String uri, byte[] _uri) implements Borsh {  

    public static CreateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateIxData createRecord(final Discriminator discriminator,
                                            final String name,
                                            final String symbol,
                                            final String uri) {
      return new CreateIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    public static CreateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var name = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var symbol = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var uri = Borsh.string(_data, i);
      return new CreateIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_name, _data, i);
      i += Borsh.writeVector(_symbol, _data, i);
      i += Borsh.writeVector(_uri, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
    }
  }

  public static final Discriminator BUY_DISCRIMINATOR = toDiscriminator(102, 6, 61, 18, 1, 218, 235, 234);

  public static Instruction buy(final AccountMeta invokedPumpProgramMeta,
                                final SolanaAccounts solanaAccounts,
                                final PublicKey globalKey,
                                final PublicKey feeRecipientKey,
                                final PublicKey mintKey,
                                final PublicKey bondingCurveKey,
                                final PublicKey associatedBondingCurveKey,
                                final PublicKey associatedUserKey,
                                final PublicKey userKey,
                                final PublicKey eventAuthorityKey,
                                final PublicKey programKey,
                                final long amount,
                                final long maxSolCost) {
    final var keys = List.of(
      createRead(globalKey),
      createWrite(feeRecipientKey),
      createRead(mintKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createWrite(associatedUserKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(BUY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, maxSolCost);

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, _data);
  }

  public record BuyIxData(Discriminator discriminator, long amount, long maxSolCost) implements Borsh {  

    public static BuyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static BuyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var maxSolCost = getInt64LE(_data, i);
      return new BuyIxData(discriminator, amount, maxSolCost);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      putInt64LE(_data, i, maxSolCost);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SELL_DISCRIMINATOR = toDiscriminator(51, 230, 133, 164, 1, 127, 131, 173);

  public static Instruction sell(final AccountMeta invokedPumpProgramMeta,
                                 final SolanaAccounts solanaAccounts,
                                 final PublicKey globalKey,
                                 final PublicKey feeRecipientKey,
                                 final PublicKey mintKey,
                                 final PublicKey bondingCurveKey,
                                 final PublicKey associatedBondingCurveKey,
                                 final PublicKey associatedUserKey,
                                 final PublicKey userKey,
                                 final PublicKey eventAuthorityKey,
                                 final PublicKey programKey,
                                 final long amount,
                                 final long minSolOutput) {
    final var keys = List.of(
      createRead(globalKey),
      createWrite(feeRecipientKey),
      createRead(mintKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createWrite(associatedUserKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(SELL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, minSolOutput);

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, _data);
  }

  public record SellIxData(Discriminator discriminator, long amount, long minSolOutput) implements Borsh {  

    public static SellIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SellIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var minSolOutput = getInt64LE(_data, i);
      return new SellIxData(discriminator, amount, minSolOutput);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      putInt64LE(_data, i, minSolOutput);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedPumpProgramMeta,
                                     final SolanaAccounts solanaAccounts,
                                     final PublicKey globalKey,
                                     final PublicKey lastWithdrawKey,
                                     final PublicKey mintKey,
                                     final PublicKey bondingCurveKey,
                                     final PublicKey associatedBondingCurveKey,
                                     final PublicKey associatedUserKey,
                                     final PublicKey userKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey) {
    final var keys = List.of(
      createRead(globalKey),
      createWrite(lastWithdrawKey),
      createRead(mintKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createWrite(associatedUserKey),
      createWritableSigner(userKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedPumpProgramMeta, keys, WITHDRAW_DISCRIMINATOR);
  }

  private PumpProgram() {
  }
}
