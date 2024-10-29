package software.sava.anchor.programs.pyth.receiver.anchor;

import java.util.List;

import software.sava.anchor.programs.pyth.receiver.anchor.types.Config;
import software.sava.anchor.programs.pyth.receiver.anchor.types.DataSource;
import software.sava.anchor.programs.pyth.receiver.anchor.types.PostUpdateAtomicParams;
import software.sava.anchor.programs.pyth.receiver.anchor.types.PostUpdateParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class PythSolanaReceiverProgram {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                       final PublicKey payerKey,
                                       final PublicKey configKey,
                                       final PublicKey systemProgramKey,
                                       final Config initialConfig) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(configKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(initialConfig)];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    Borsh.write(initialConfig, _data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator, Config initialConfig) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var initialConfig = Config.read(_data, i);
      return new InitializeIxData(discriminator, initialConfig);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(initialConfig, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(initialConfig);
    }
  }

  public static final Discriminator REQUEST_GOVERNANCE_AUTHORITY_TRANSFER_DISCRIMINATOR = toDiscriminator(92, 18, 67, 156, 27, 151, 183, 224);

  public static Instruction requestGovernanceAuthorityTransfer(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                                               final PublicKey payerKey,
                                                               final PublicKey configKey,
                                                               final PublicKey targetGovernanceAuthority) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(REQUEST_GOVERNANCE_AUTHORITY_TRANSFER_DISCRIMINATOR, _data, 0);
    targetGovernanceAuthority.write(_data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record RequestGovernanceAuthorityTransferIxData(Discriminator discriminator, PublicKey targetGovernanceAuthority) implements Borsh {  

    public static RequestGovernanceAuthorityTransferIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static RequestGovernanceAuthorityTransferIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var targetGovernanceAuthority = readPubKey(_data, i);
      return new RequestGovernanceAuthorityTransferIxData(discriminator, targetGovernanceAuthority);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      targetGovernanceAuthority.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ACCEPT_GOVERNANCE_AUTHORITY_TRANSFER_DISCRIMINATOR = toDiscriminator(254, 39, 222, 79, 64, 217, 205, 127);

  public static Instruction acceptGovernanceAuthorityTransfer(final AccountMeta invokedPythSolanaReceiverProgramMeta, final PublicKey payerKey, final PublicKey configKey) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, ACCEPT_GOVERNANCE_AUTHORITY_TRANSFER_DISCRIMINATOR);
  }

  public static final Discriminator SET_DATA_SOURCES_DISCRIMINATOR = toDiscriminator(107, 73, 15, 119, 195, 116, 91, 210);

  public static Instruction setDataSources(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                           final PublicKey payerKey,
                                           final PublicKey configKey,
                                           final DataSource[] validDataSources) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(validDataSources)];
    int i = writeDiscriminator(SET_DATA_SOURCES_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(validDataSources, _data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record SetDataSourcesIxData(Discriminator discriminator, DataSource[] validDataSources) implements Borsh {  

    public static SetDataSourcesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetDataSourcesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validDataSources = Borsh.readVector(DataSource.class, DataSource::read, _data, i);
      return new SetDataSourcesIxData(discriminator, validDataSources);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(validDataSources, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(validDataSources);
    }
  }

  public static final Discriminator SET_FEE_DISCRIMINATOR = toDiscriminator(18, 154, 24, 18, 237, 214, 19, 80);

  public static Instruction setFee(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                   final PublicKey payerKey,
                                   final PublicKey configKey,
                                   final long singleUpdateFeeInLamports) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, singleUpdateFeeInLamports);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record SetFeeIxData(Discriminator discriminator, long singleUpdateFeeInLamports) implements Borsh {  

    public static SetFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var singleUpdateFeeInLamports = getInt64LE(_data, i);
      return new SetFeeIxData(discriminator, singleUpdateFeeInLamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, singleUpdateFeeInLamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_WORMHOLE_ADDRESS_DISCRIMINATOR = toDiscriminator(154, 174, 252, 157, 91, 215, 179, 156);

  public static Instruction setWormholeAddress(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                               final PublicKey payerKey,
                                               final PublicKey configKey,
                                               final PublicKey wormhole) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(SET_WORMHOLE_ADDRESS_DISCRIMINATOR, _data, 0);
    wormhole.write(_data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record SetWormholeAddressIxData(Discriminator discriminator, PublicKey wormhole) implements Borsh {  

    public static SetWormholeAddressIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetWormholeAddressIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var wormhole = readPubKey(_data, i);
      return new SetWormholeAddressIxData(discriminator, wormhole);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      wormhole.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_MINIMUM_SIGNATURES_DISCRIMINATOR = toDiscriminator(5, 210, 206, 124, 43, 68, 104, 149);

  public static Instruction setMinimumSignatures(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                                 final PublicKey payerKey,
                                                 final PublicKey configKey,
                                                 final int minimumSignatures) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createWrite(configKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(SET_MINIMUM_SIGNATURES_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) minimumSignatures;

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record SetMinimumSignaturesIxData(Discriminator discriminator, int minimumSignatures) implements Borsh {  

    public static SetMinimumSignaturesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetMinimumSignaturesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minimumSignatures = _data[i] & 0xFF;
      return new SetMinimumSignaturesIxData(discriminator, minimumSignatures);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) minimumSignatures;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator POST_UPDATE_ATOMIC_DISCRIMINATOR = toDiscriminator(49, 172, 84, 192, 175, 180, 52, 234);

  public static Instruction postUpdateAtomic(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                             final PublicKey payerKey,
                                             // Instead we do the same steps in deserialize_guardian_set_checked.
                                             final PublicKey guardianSetKey,
                                             final PublicKey configKey,
                                             final PublicKey treasuryKey,
                                             // The contraint is such that either the price_update_account is uninitialized or the payer is the write_authority.
                                             // Pubkey::default() is the SystemProgram on Solana and it can't sign so it's impossible that price_update_account.write_authority == Pubkey::default() once the account is initialized
                                             final PublicKey priceUpdateAccountKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey writeAuthorityKey,
                                             final PostUpdateAtomicParams params) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(guardianSetKey),
      createRead(configKey),
      createWrite(treasuryKey),
      createWritableSigner(priceUpdateAccountKey),
      createRead(systemProgramKey),
      createReadOnlySigner(writeAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(POST_UPDATE_ATOMIC_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record PostUpdateAtomicIxData(Discriminator discriminator, PostUpdateAtomicParams params) implements Borsh {  

    public static PostUpdateAtomicIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PostUpdateAtomicIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PostUpdateAtomicParams.read(_data, i);
      return new PostUpdateAtomicIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator POST_UPDATE_DISCRIMINATOR = toDiscriminator(133, 95, 207, 175, 11, 79, 118, 44);

  public static Instruction postUpdate(final AccountMeta invokedPythSolanaReceiverProgramMeta,
                                       final PublicKey payerKey,
                                       final PublicKey encodedVaaKey,
                                       final PublicKey configKey,
                                       final PublicKey treasuryKey,
                                       // The contraint is such that either the price_update_account is uninitialized or the payer is the write_authority.
                                       // Pubkey::default() is the SystemProgram on Solana and it can't sign so it's impossible that price_update_account.write_authority == Pubkey::default() once the account is initialized
                                       final PublicKey priceUpdateAccountKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey writeAuthorityKey,
                                       final PostUpdateParams params) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(encodedVaaKey),
      createRead(configKey),
      createWrite(treasuryKey),
      createWritableSigner(priceUpdateAccountKey),
      createRead(systemProgramKey),
      createReadOnlySigner(writeAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(POST_UPDATE_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, _data);
  }

  public record PostUpdateIxData(Discriminator discriminator, PostUpdateParams params) implements Borsh {  

    public static PostUpdateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PostUpdateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PostUpdateParams.read(_data, i);
      return new PostUpdateIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator RECLAIM_RENT_DISCRIMINATOR = toDiscriminator(218, 200, 19, 197, 227, 89, 192, 22);

  public static Instruction reclaimRent(final AccountMeta invokedPythSolanaReceiverProgramMeta, final PublicKey payerKey, final PublicKey priceUpdateAccountKey) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(priceUpdateAccountKey)
    );

    return Instruction.createInstruction(invokedPythSolanaReceiverProgramMeta, keys, RECLAIM_RENT_DISCRIMINATOR);
  }

  private PythSolanaReceiverProgram() {
  }
}
